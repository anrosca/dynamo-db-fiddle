package inc.evil.aws.fiddle.topic.web;

import java.util.List;

import inc.evil.aws.fiddle.topic.domain.TopicTag;
import inc.evil.aws.fiddle.topic.facade.TopicFacade;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories/{categoryId}/topics")
public class TopicController {
    private final TopicFacade topicFacade;

    public TopicController(TopicFacade topicFacade) {
        this.topicFacade = topicFacade;
    }

    @GetMapping
    public List<TopicResponse> findAll(@PathVariable String categoryId) {
        return topicFacade.findAllFor(categoryId);
    }

    @GetMapping("{topicId}")
    public TopicResponse findById(@PathVariable String categoryId, @PathVariable String topicId) {
        return topicFacade.findById(categoryId, topicId);
    }

    @PostMapping("{topicId}/tags")
    public TopicResponse addTags(@PathVariable String categoryId, @PathVariable String topicId, @RequestBody List<TopicTag> tagsToAdd) {
        return topicFacade.addTags(categoryId, topicId, tagsToAdd);
    }

    @DeleteMapping("{topicId}")
    public TopicResponse deleteById(@PathVariable String categoryId, @PathVariable String topicId) {
        return topicFacade.deleteById(categoryId, topicId);
    }

    @PostMapping
    public TopicResponse create(@RequestBody CreateTopicRequest topicToCreate, @PathVariable String categoryId) {
        return topicFacade.create(topicToCreate, categoryId);
    }
}
