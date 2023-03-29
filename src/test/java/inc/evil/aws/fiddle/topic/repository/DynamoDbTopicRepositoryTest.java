package inc.evil.aws.fiddle.topic.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.common.AbstractDatabaseTest;
import inc.evil.aws.fiddle.common.DynamoCsv;
import inc.evil.aws.fiddle.common.DynamoDbTest;
import inc.evil.aws.fiddle.topic.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DynamoDbTest(repositories = DynamoDbTopicRepository.class)
public class DynamoDbTopicRepositoryTest extends AbstractDatabaseTest {

    @Autowired
    private DynamoDbTopicRepository topicRepository;

    @Test
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    public void shouldBeAbleToFindAllTopicsForCategory() {
        String categoryId = "501735c3-5da7-4684-82d3-37af5d5dc44f";
        List<Topic> expectedTopics = List.of(
            Topic.builder()
                 .title("AWS stuff")
                 .userId("44af5d5d313f")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#301735c3-3da7-4684-33d3-37af5d5d313f")
                 .build(),
            Topic.builder()
                 .title("Java 19 released")
                 .userId("37af5d5d313f")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#101735c3-1da7-4684-11d3-17af5d5dc11f")
                 .build()
        );

        List<Topic> actualTopics = topicRepository.findByCategory(categoryId);

        assertThat(actualTopics).isEqualTo(expectedTopics);
    }

    @Test
    @DynamoCsv(value = "/test-data/topics/one-topic.csv", entityType = Topic.class)
    public void shouldBeAbleToFindTopicsByTopicIdCategoryId() {
        String categoryId = "501735c3-5da7-4684-82d3-37af5d5dc44f";
        String topicId = "101735c3-1da7-4684-11d3-17af5d5dc11f";
        Topic expectedTopic = Topic.builder()
                 .title("Java 19 released")
                 .userId("37af5d5d313f")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#101735c3-1da7-4684-11d3-17af5d5dc11f")
                 .build();

        Optional<Topic> actualTopic = topicRepository.findByCategoryIdAndTopicId(categoryId, topicId);

        assertThat(actualTopic).isEqualTo(Optional.of(expectedTopic));
    }

    @Test
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    public void shouldBeAbleToDeleteTopicByCategoryIdAndTopicId() {
        String categoryId = "501735c3-5da7-4684-82d3-37af5d5dc44f";
        String topicId = "101735c3-1da7-4684-11d3-17af5d5dc11f";
        List<Topic> expectedRemainingTopics = List.of(
            Topic.builder()
                 .title("AWS stuff")
                 .userId("44af5d5d313f")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#301735c3-3da7-4684-33d3-37af5d5d313f")
                 .build()
        );
        Topic expectedDeletedTopic = Topic.builder()
                                   .title("Java 19 released")
                                   .userId("37af5d5d313f")
                                   .createdAt("2023-03-28T14:15:16")
                                   .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                                   .sortKey("Topic#101735c3-1da7-4684-11d3-17af5d5dc11f")
                                   .build();

        Optional<Topic> actualDeletedTopic = topicRepository.deleteByCategoryIdAndTopicId(categoryId, topicId);

        assertThat(actualDeletedTopic).isEqualTo(Optional.of(expectedDeletedTopic));
        assertThat(topicRepository.findByCategory(categoryId)).isEqualTo(expectedRemainingTopics);
    }

    @Test
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    public void shouldBeAbleToFindAllTopicsForGivenUser() {
        String userId = "44af5d5d313f";
        List<Topic> expectedTopics = List.of(
            Topic.builder()
                 .title("AWS stuff")
                 .userId("44af5d5d313f")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#301735c3-3da7-4684-33d3-37af5d5d313f")
                 .build()
        );

        List<Topic> actualTopics = topicRepository.findTopicsForUser(userId);

        assertThat(actualTopics).isEqualTo(expectedTopics);
    }
}
