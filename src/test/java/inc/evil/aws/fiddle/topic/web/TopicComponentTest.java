package inc.evil.aws.fiddle.topic.web;

import inc.evil.aws.fiddle.category.domain.Category;
import inc.evil.aws.fiddle.common.AbstractWebIntegrationTest;
import inc.evil.aws.fiddle.common.ComponentTest;
import inc.evil.aws.fiddle.common.DynamoCsv;
import inc.evil.aws.fiddle.topic.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ComponentTest
public class TopicComponentTest extends AbstractWebIntegrationTest {

    @Test
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    @DynamoCsv(value = "/test-data/categories/two-categories.csv", entityType = Category.class)
    public void shouldBeAbleToFindAllTopicsForGivenCategory() {
        TopicResponse[] expectedTopics = {
            TopicResponse.builder()
                         .id("101735c3-1da7-4684-11d3-17af5d5dc11f")
                         .name("Java 19 released")
                         .createdAt("2023-03-29T20:03:27.757321136Z")
                         .categoryId("501735c3-5da7-4684-82d3-37af5d5dc44f")
                .build(),
            TopicResponse.builder()
                         .id("301735c3-3da7-4684-33d3-37af5d5d313f")
                         .name("AWS stuff")
                         .createdAt("2023-03-29T20:03:27.757321136Z")
                         .categoryId("501735c3-5da7-4684-82d3-37af5d5dc44f")
                .build()
        };
        RequestEntity<Void> request = makeRequestFor("/api/v1/categories/501735c3-5da7-4684-82d3-37af5d5dc44f/topics", HttpMethod.GET);

        ResponseEntity<TopicResponse[]> response = restTemplate.exchange(request, TopicResponse[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedTopics);
    }

    @Test
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    @DynamoCsv(value = "/test-data/categories/two-categories.csv", entityType = Category.class)
    public void shouldBeAbleToFindTopicByCategoryIdAndTopicId() {
        TopicResponse expectedTopic =
            TopicResponse.builder()
                         .id("101735c3-1da7-4684-11d3-17af5d5dc11f")
                         .name("Java 19 released")
                         .createdAt("2023-03-29T20:03:27.757321136Z")
                         .categoryId("501735c3-5da7-4684-82d3-37af5d5dc44f")
                         .build();
        RequestEntity<Void> request =
            makeRequestFor("/api/v1/categories/501735c3-5da7-4684-82d3-37af5d5dc44f/topics/101735c3-1da7-4684-11d3-17af5d5dc11f", HttpMethod.GET);

        ResponseEntity<TopicResponse> response = restTemplate.exchange(request, TopicResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedTopic);
    }
}
