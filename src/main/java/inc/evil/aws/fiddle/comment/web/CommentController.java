package inc.evil.aws.fiddle.comment.web;

import java.util.List;

import inc.evil.aws.fiddle.comment.facade.CommentFacade;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/topics/{topicId}/comments")
public class CommentController {
    private final CommentFacade commentFacade;

    public CommentController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @GetMapping
    public List<CommentResponse> findAll(@PathVariable String categoryId, @PathVariable String topicId) {
        return commentFacade.findAllForTopic(topicId);
    }

    @GetMapping("{commentId}")
    public CommentResponse findById(@PathVariable String categoryId, @PathVariable String topicId, @PathVariable String commentId) {
        return commentFacade.findById(topicId, commentId);
    }

    @PostMapping("{commentId}/likes")
    public CommentResponse likeComment(@PathVariable String categoryId, @PathVariable String topicId, @PathVariable String commentId) {
        return commentFacade.likeComment(topicId, commentId);
    }

    @DeleteMapping("{commentId}")
    public CommentResponse deleteById(@PathVariable String categoryId, @PathVariable String topicId, @PathVariable String commentId) {
        return commentFacade.deleteById(topicId, commentId);
    }

    @PostMapping
    public CommentResponse create(@PathVariable String categoryId, @PathVariable String topicId, @RequestBody CreateCommentRequest request) {
        return commentFacade.create(topicId, request);
    }
}
