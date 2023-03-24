package inc.evil.aws.fiddle.comment.web;

import inc.evil.aws.fiddle.comment.domain.Comment;
import lombok.Builder;

@Builder
public record CommentResponse(String author, String createdAt, String text, String topicId, String commentId) {

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                              .commentId(comment.getId())
                              .author(comment.getAuthor())
                              .text(comment.getText())
                              .createdAt(comment.getCreatedAt())
                              .topicId(comment.getTopicId())
                              .build();
    }
}
