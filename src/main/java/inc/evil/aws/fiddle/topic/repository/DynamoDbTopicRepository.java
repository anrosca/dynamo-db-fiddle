package inc.evil.aws.fiddle.topic.repository;

import java.util.*;

import inc.evil.aws.fiddle.dynamodb.config.IndexNames;
import inc.evil.aws.fiddle.topic.domain.Topic;
import inc.evil.aws.fiddle.topic.domain.TopicTag;
import inc.evil.aws.fiddle.user.domain.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
public class DynamoDbTopicRepository implements TopicRepository {

    private final DynamoDbTable<Topic> topicTable;
    private final DynamoDbIndex<Topic> topicGsi1Index;

    public DynamoDbTopicRepository(DynamoDbTable<Topic> topicTable) {
        this.topicTable = topicTable;
        this.topicGsi1Index = topicTable.index(IndexNames.GS1_INDEX_NAME);
    }

    @Override
    public List<Topic> findByCategory(String categoryId) {
        QueryConditional skBeginsWithQuery = QueryConditional.sortBeginsWith(
            Key.builder()
               .partitionValue(Topic.TopicKeyBuilder.makePartitionKey(categoryId))
               .sortValue(Topic.TOPIC_SK_PREFIX)
               .build()
        );

        return topicTable.query(skBeginsWithQuery)
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

    @Override
    public List<Topic> findTopicsForUser(String id) {
        Key key = Key.builder()
                     .partitionValue(User.USER_PK_PREFIX + id)
                     .sortValue(Topic.TOPIC_SK_PREFIX)
                     .build();
        QueryConditional queryConditional = QueryConditional.sortBeginsWith(key);
        return topicGsi1Index.query(queryConditional)
                             .stream()
                             .map(Page::items)
                             .flatMap(Collection::stream)
                             .toList();
    }

    @Override
    public Optional<Topic> addTags(String categoryId, String topicId, List<TopicTag> tagsToAdd) {
        return findByCategoryIdAndTopicId(categoryId, topicId)
                .map(topic -> {
                    topic.setTags(mergeTopicTags(tagsToAdd, topic));
                    return topic;
                })
                .map(topicTable::updateItem);
    }

    private static List<TopicTag> mergeTopicTags(List<TopicTag> tagsToAdd, Topic topic) {
        Set<TopicTag> mergedTags = new HashSet<>(tagsToAdd);
        mergedTags.addAll(topic.getTags() != null ? topic.getTags() : List.of());
        return mergedTags.stream().toList();
    }
}
