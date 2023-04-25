package inc.evil.aws.fiddle.comment.web;

import java.time.Instant;

import inc.evil.aws.fiddle.comment.domain.Comment;

public record CreateCommentRequest(String userName, String text) {

    public Comment toComment(String topicId) {
        return Comment.builder()
                .userName(userName)
                .text(text)
                .createdAt(Instant.now())
                .topicId(topicId)
                .likeCount(0L)
                .build();
    }
}
