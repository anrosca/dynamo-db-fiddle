package inc.evil.aws.fiddle.comment.web;

import java.time.Instant;

import inc.evil.aws.fiddle.comment.domain.Comment;

public record CreateCommentRequest(String author, String text) {

    public Comment toComment(String topicId) {
        return Comment.builder()
                      .author(author)
                      .text(text)
                      .createdAt(Instant.now().toString())
                      .topicId(topicId)
                      .build();
    }
}
