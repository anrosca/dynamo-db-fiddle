package inc.evil.aws.fiddle.comment.web;

import java.time.Instant;

import inc.evil.aws.fiddle.comment.domain.Comment;

public record CreateCommentRequest(String userId, String text) {

    public Comment toComment(String topicId) {
        return Comment.builder()
                      .userId(userId)
                      .text(text)
                      .createdAt(Instant.now())
                      .topicId(topicId)
                      .build();
    }
}
