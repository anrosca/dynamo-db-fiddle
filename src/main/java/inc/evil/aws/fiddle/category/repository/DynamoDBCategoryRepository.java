package inc.evil.aws.fiddle.category.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import inc.evil.aws.fiddle.category.domain.Category;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
public class DynamoDBCategoryRepository implements CategoryRepository {

    private final DynamoDbTable<Category> categoryTable;

    public DynamoDBCategoryRepository(DynamoDbTable<Category> categoryTable) {
        this.categoryTable = categoryTable;
    }

    @Override
    public List<Category> findAll() {
        Expression expression = Expression.builder()
                                          .expression("begins_with(SK, :skPrefix)")
                                          .expressionValues(Map.of(":skPrefix", AttributeValue.builder().s(Category.CATEGORY_SK_PREFIX).build()))
                                          .build();
        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                                                                     .filterExpression(expression)
                                                                     .build();
        return categoryTable.scan(scanEnhancedRequest)
                            .stream()
                            .flatMap(page -> page.items().stream())
                            .toList();
    }

    @Override
    public Category create(Category categoryToCreate) {
        categoryTable.putItem(categoryToCreate);
        return categoryToCreate;
    }

    @Override
    public Optional<Category> findById(String id) {
        Key key = Key.builder()
                     .partitionValue(Category.CategoryKeyBuilder.makePartitionKey(id))
                     .sortValue(Category.CategoryKeyBuilder.makeSortKey(id))
                     .build();
        return Optional.ofNullable(categoryTable.getItem(key));
    }

    @Override
    public Optional<Category> deleteById(String id) {
        Key key = Key.builder()
                     .partitionValue(Category.CategoryKeyBuilder.makePartitionKey(id))
                     .sortValue(Category.CategoryKeyBuilder.makeSortKey(id))
                     .build();
        return Optional.ofNullable(categoryTable.deleteItem(key));
    }
}
