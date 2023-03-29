package inc.evil.aws.fiddle.comment.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.comment.domain.Comment;
import inc.evil.aws.fiddle.common.AbstractDatabaseTest;
import inc.evil.aws.fiddle.common.DynamoCsv;
import inc.evil.aws.fiddle.common.DynamoDbTest;
import inc.evil.aws.fiddle.topic.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DynamoDbTest(repositories = DynamoDbCommentRepository.class)
public class DynamoDbCommentRepositoryTest extends AbstractDatabaseTest {

    @Autowired
    private DynamoDbCommentRepository commentRepository;

    @Test
    @DynamoCsv(value = "/test-data/comment/one-comment.csv", entityType = Comment.class)
    public void shouldBeAbleToFindCommentByTopicIdAndCommentId() {
        String topicId = "101735c3-1da7-4684-11d3-17af5d5dc11f";
        String commentId = "501735c3-5da7-4684-82d3-37af5d5dc44f";
        Comment expectedComment = Comment.builder()
                                         .id(commentId)
                                         .userId("101735c3-1da7-4684-11d3-17af5d5dc11f")
                                         .text("Hello, world")
                                         .createdAt("2023-03-28T14:15:16")
                                         .topicId(topicId)
                                         .build();

        Optional<Comment> actualComment = commentRepository.findById(topicId, commentId);

        assertThat(actualComment).isEqualTo(Optional.of(expectedComment));
    }

    @Test
    @DynamoCsv(value = "/test-data/comment/two-comments.csv", entityType = Comment.class)
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    public void shouldBeAbleToFindCommentsByTopicId() {
        String topicId = "101735c3-1da7-4684-11d3-17af5d5dc11f";
        List<Comment> expectedComments = List.of(
            Comment.builder()
                   .id("601735c3-6da7-4684-82d3-67af5d5dc44f")
                   .userId("201735c3-1da7-4684-11d3-17af5d5dc11f")
                   .text("Yay")
                   .createdAt("2023-03-29T14:15:16")
                   .topicId(topicId)
                   .build(),
            Comment.builder()
                   .id("501735c3-5da7-4684-82d3-37af5d5dc44f")
                   .userId("101735c3-1da7-4684-11d3-17af5d5dc11f")
                   .text("Hello, world")
                   .createdAt("2023-03-28T14:15:16")
                   .topicId(topicId)
                   .build()
        );

        List<Comment> actualComments = commentRepository.findByTopic(topicId);

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    @DynamoCsv(value = "/test-data/comment/two-comments.csv", entityType = Comment.class)
    public void shouldBeAbleToDeleteCommentsByTopicIdAndCommentId() {
        String topicId = "101735c3-1da7-4684-11d3-17af5d5dc11f";
        String commentId = "501735c3-5da7-4684-82d3-37af5d5dc44f";
        List<Comment> expectedRemainingComments = List.of(
            Comment.builder()
                   .id("601735c3-6da7-4684-82d3-67af5d5dc44f")
                   .userId("201735c3-1da7-4684-11d3-17af5d5dc11f")
                   .text("Yay")
                   .createdAt("2023-03-29T14:15:16")
                   .topicId(topicId)
                   .build()
        );
        Comment expectedDeletedComment = Comment.builder()
                                                .id("501735c3-5da7-4684-82d3-37af5d5dc44f")
                                                .userId("101735c3-1da7-4684-11d3-17af5d5dc11f")
                                                .text("Hello, world")
                                                .createdAt("2023-03-28T14:15:16")
                                                .topicId(topicId)
                                                .build();

        Optional<Comment> actualDeletedComment = commentRepository.deleteById(topicId, commentId);

        assertThat(actualDeletedComment).isEqualTo(Optional.of(expectedDeletedComment));
        assertThat(commentRepository.findByTopic(topicId)).isEqualTo(expectedRemainingComments);
    }
}
