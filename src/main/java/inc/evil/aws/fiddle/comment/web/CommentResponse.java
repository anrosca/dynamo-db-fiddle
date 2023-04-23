package inc.evil.aws.fiddle.comment.web;

import inc.evil.aws.fiddle.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentResponse(String author, String createdAt, String text, String topicId, String commentId,
                              long likeCount) {

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .author(comment.getUserId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt().toString())
                .topicId(comment.getTopicId())
                .likeCount(comment.getLikeCount())
                .build();
    }
}
