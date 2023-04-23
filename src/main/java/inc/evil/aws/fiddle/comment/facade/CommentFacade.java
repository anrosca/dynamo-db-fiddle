package inc.evil.aws.fiddle.comment.facade;

import java.util.List;

import inc.evil.aws.fiddle.comment.service.CommentService;
import inc.evil.aws.fiddle.comment.web.CommentResponse;
import inc.evil.aws.fiddle.comment.web.CreateCommentRequest;
import org.springframework.stereotype.Component;

@Component
public class CommentFacade {
    private final CommentService commentService;

    public CommentFacade(CommentService commentService) {
        this.commentService = commentService;
    }

    public List<CommentResponse> findAllForTopic(String topicId) {
        return commentService.findAllForTopic(topicId)
                             .stream()
                             .map(CommentResponse::from)
                             .toList();
    }

    public CommentResponse create(String topicId, CreateCommentRequest request) {
        return CommentResponse.from(commentService.create(request.toComment(topicId)));
    }

    public CommentResponse findById(String topicId, String commentId) {
        return CommentResponse.from(commentService.findById(topicId, commentId));
    }

    public CommentResponse deleteById(String topicId, String commentId) {
        return CommentResponse.from(commentService.deleteById(topicId, commentId));
    }

    public CommentResponse likeComment(String topicId, String commentId) {
        return CommentResponse.from(commentService.likeComment(topicId, commentId));
    }
}
