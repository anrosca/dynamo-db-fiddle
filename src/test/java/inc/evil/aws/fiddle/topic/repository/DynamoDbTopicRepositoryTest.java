package inc.evil.aws.fiddle.topic.repository;

import java.util.List;

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
                 .title("Java 19 released")
                 .author("Mike")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#101735c3-1da7-4684-11d3-17af5d5dc11f")
                 .build(),
            Topic.builder()
                 .title("AWS stuff")
                 .author("John")
                 .createdAt("2023-03-28T14:15:16")
                 .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                 .sortKey("Topic#301735c3-3da7-4684-33d3-37af5d5d313f")
                 .build()
        );

        List<Topic> actualTopics = topicRepository.findByCategory(categoryId);

        assertThat(actualTopics).isEqualTo(expectedTopics);
    }
}
