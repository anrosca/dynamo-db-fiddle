package inc.evil.aws.fiddle.comment.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.comment.domain.Comment;

public interface CommentRepository {
    List<Comment> findByTopic(String topicId);

    Comment create(Comment commentToCreate);

    Optional<Comment> findByTopicIdAndCommentId(String topicId, String commentId);

    Optional<Comment> findById(String topicId, String commentId);

    Optional<Comment> deleteById(String topicId, String commentId);

    Optional<Comment> likeComment(String topicId, String commentId);
}
