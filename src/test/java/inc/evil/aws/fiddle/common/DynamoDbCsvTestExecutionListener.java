package inc.evil.aws.fiddle.common;

import java.io.IOException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import inc.evil.aws.fiddle.dynamodb.config.properties.AwsProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class DynamoDbCsvTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        DynamoCsv dynamoCsv = testContext.getTestMethod().getAnnotation(DynamoCsv.class);
        String resourcePath = dynamoCsv.value();
        Class<?> entityType = dynamoCsv.entityType();

        DynamoDbTable<Object> table = makeDynamoDbTable(applicationContext, entityType);

        if (dynamoCsv.reCreateTable()) {
            dropAndCreateTable(table);
        }
        if (!resourcePath.isEmpty()) {
            readAndPutItems(applicationContext, resourcePath, entityType, table);
        }
    }

    private static void readAndPutItems(ApplicationContext applicationContext, String resourcePath, Class<?> entityType, DynamoDbTable<Object> table)
        throws IOException {
        try (MappingIterator<Object> mappingIterator = parseCvs(entityType, applicationContext.getResource(resourcePath))) {
            mappingIterator.readAll().forEach(table::putItem);
        }
    }

    private static void dropAndCreateTable(DynamoDbTable<Object> table) {
        table.deleteTable();
        table.createTable();
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
