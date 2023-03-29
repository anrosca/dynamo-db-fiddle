package inc.evil.aws.fiddle.topic.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.topic.domain.Topic;

public interface TopicRepository {
    List<Topic> findByCategory(String categoryId);

    Topic create(Topic topicToCreate);

    Optional<Topic> findByCategoryIdAndTopicId(String categoryId, String topicId);

    Optional<Topic> deleteByCategoryIdAndTopicId(String categoryId, String topicId);

    List<Topic> findTopicsForUser(String id);
}
