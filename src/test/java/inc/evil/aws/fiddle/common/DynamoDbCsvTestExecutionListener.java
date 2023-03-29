package inc.evil.aws.fiddle.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import inc.evil.aws.fiddle.dynamodb.config.properties.AwsProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.util.TestContextResourceUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class DynamoDbCsvTestExecutionListener implements TestExecutionListener {
    private final AtomicBoolean cleanupFlag = new AtomicBoolean();

    @Override
    public void beforeTestMethod(TestContext testContext) {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        DynamoCsv[] annotations = testContext.getTestMethod().getAnnotationsByType(DynamoCsv.class);
        boolean shouldCleanupTable = shouldCleanupTable(annotations);
        for (DynamoCsv dynamoCsv : annotations) {
            executeFor(applicationContext, dynamoCsv, shouldCleanupTable);
        }
    }

    private boolean shouldCleanupTable(DynamoCsv[] annotations) {
        return Arrays.stream(annotations)
                     .map(DynamoCsv::cleanupTable)
                     .reduce(false, (a, b) -> a | b);
    }

    private void executeFor(ApplicationContext applicationContext, DynamoCsv dynamoCsv, boolean shouldCleanupTable) {
        String resourcePath = dynamoCsv.value();
        Class<?> entityType = dynamoCsv.entityType();

        DynamoDbTable<Object> table = makeDynamoDbTable(applicationContext, entityType);

        if (shouldCleanupTable) {
            cleanupTable(table);
        }

        if (!resourcePath.isEmpty()) {
            readAndPutItems(applicationContext, resourcePath, entityType, table);
        }
    }

    private static void readAndPutItems(ApplicationContext applicationContext, String resourcePath, Class<?> entityType, DynamoDbTable<Object> table) {
        List<Resource> resources = TestContextResourceUtils.convertToResourceList(applicationContext, "classpath:" + resourcePath);
        try (MappingIterator<Object> mappingIterator = parseCvs(entityType, resources.get(0))) {
            mappingIterator.readAll().forEach(table::putItem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanupTable(DynamoDbTable<Object> table) {
        if (!cleanupFlag.get()) {
            table.scan()
                 .stream()
                 .flatMap(page -> page.items().stream())
                 .forEach(table::deleteItem);
        }
        cleanupFlag.compareAndSet(false, true);
    }

    @SuppressWarnings("unchecked")
    private static DynamoDbTable<Object> makeDynamoDbTable(ApplicationContext applicationContext, Class<?> entityType) {
        DynamoDbEnhancedClient dynamoDbEnhancedClient = applicationContext.getBean(DynamoDbEnhancedClient.class);
        AwsProperties awsProperties = applicationContext.getBean(AwsProperties.class);
        return (DynamoDbTable<Object>) dynamoDbEnhancedClient.table(awsProperties.dynamoDbTableName(), TableSchema.fromBean(entityType));
    }

    private static MappingIterator<Object> parseCvs(Class<?> entityType, Resource resource) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        return csvMapper.readerFor(entityType)
                        .with(schema)
                        .readValues(resource.getInputStream());
    }
}
