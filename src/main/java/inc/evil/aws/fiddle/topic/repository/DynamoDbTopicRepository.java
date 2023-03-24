package inc.evil.aws.fiddle.topic.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.topic.domain.Topic;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Repository
public class DynamoDbTopicRepository implements TopicRepository {
    private final DynamoDbTable<Topic> topicTable;

    public DynamoDbTopicRepository(DynamoDbTable<Topic> topicTable) {
        this.topicTable = topicTable;
    }

    @Override
    public List<Topic> findByCategory(String categoryId) {
        QueryConditional skBeginsWithQuery = buildSkBeginsWithKeyQuery(categoryId);

        QueryEnhancedRequest reverseOrderQuery = QueryEnhancedRequest
            .builder()
            .queryConditional(skBeginsWithQuery)
            .scanIndexForward(Boolean.FALSE)
            .build();

        return topicTable.query(reverseOrderQuery)
                         .items()
                         .stream()
                         .toList();
    }

    @Override
    public Topic create(Topic topicToCreate) {
        topicTable.putItem(topicToCreate);
        return topicToCreate;
    }

    @Override
    public Optional<Topic> findByCategoryIdAndTopicId(String categoryId, String topicId) {
        Key key = Key.builder()
                     .partitionValue(Topic.TopicKeyBuilder.makePartitionKey(categoryId))
                     .sortValue(Topic.TopicKeyBuilder.makeSortKey(topicId))
                     .build();
        return Optional.ofNullable(topicTable.getItem(key));
    }

    @Override
    public Optional<Topic> deleteByCategoryIdAndTopicId(String categoryId, String topicId) {
        Key key = Key.builder()
                     .partitionValue(Topic.TopicKeyBuilder.makePartitionKey(categoryId))
                     .sortValue(Topic.TopicKeyBuilder.makeSortKey(topicId))
                     .build();
        return Optional.ofNullable(topicTable.deleteItem(key));
    }

    private QueryConditional buildSkBeginsWithKeyQuery(String categoryId) {
        return QueryConditional.sortBeginsWith(
            Key.builder()
               .partitionValue(Topic.TopicKeyBuilder.makePartitionKey(categoryId))
               .sortValue(Topic.TOPIC_SK_PREFIX)
               .build()
        );
    }
}
