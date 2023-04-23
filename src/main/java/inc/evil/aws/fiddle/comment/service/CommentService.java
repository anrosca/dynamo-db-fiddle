package inc.evil.aws.fiddle.comment.service;

import java.util.List;

import inc.evil.aws.fiddle.comment.domain.Comment;
import inc.evil.aws.fiddle.comment.repository.CommentRepository;
import inc.evil.aws.fiddle.common.IdGenerator;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final IdGenerator idGenerator;

    public CommentService(CommentRepository commentRepository, IdGenerator idGenerator) {
        this.commentRepository = commentRepository;
        this.idGenerator = idGenerator;
    }

    public List<Comment> findAllForTopic(String topicId) {
        return commentRepository.findByTopic(topicId);
    }

    public Comment create(Comment commentToCreate) {
        commentToCreate.setId(idGenerator.generate());
        return commentRepository.create(commentToCreate);
    }

    public Comment findById(String topicId, String commentId) {
        return commentRepository.findById(topicId, commentId)
                                .orElseThrow(
                                    () -> new CommentNotFoundException("No comment for topic: " + topicId + " and commentId: " + commentId + "was found"));
    }

    public Comment deleteById(String topicId, String commentId) {
        return commentRepository.deleteById(topicId, commentId)
                                .orElseThrow(
                                    () -> new CommentNotFoundException("No comment for topic: " + topicId + " and commentId: " + commentId + "was found"));
    }

    public Comment likeComment(String topicId, String commentId) {
        return commentRepository.likeComment(topicId, commentId)
                .orElseThrow(
                        () -> new CommentNotFoundException("No comment for topic: " + topicId + " and commentId: " + commentId + "was found"));
    }
}
