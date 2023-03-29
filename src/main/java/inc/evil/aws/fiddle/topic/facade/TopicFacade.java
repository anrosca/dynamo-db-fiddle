package inc.evil.aws.fiddle.topic.facade;

import java.util.List;

import inc.evil.aws.fiddle.topic.service.TopicService;
import inc.evil.aws.fiddle.topic.web.CreateTopicRequest;
import inc.evil.aws.fiddle.topic.web.TopicResponse;
import org.springframework.stereotype.Component;

@Component
public class TopicFacade {

    private final TopicService topicService;

    public TopicFacade(TopicService topicService) {
        this.topicService = topicService;
    }

    public List<TopicResponse> findAllFor(String categoryId) {
        return topicService.findAllFor(categoryId)
                           .stream()
                           .map(TopicResponse::from)
                           .toList();
    }

    public TopicResponse create(CreateTopicRequest topicToCreate, String categoryId) {
        return TopicResponse.from(topicService.create(topicToCreate.toTopic(categoryId)));
    }

    public TopicResponse findById(String categoryId, String topicId) {
        return TopicResponse.from(topicService.findById(categoryId, topicId));
    }

    public TopicResponse deleteById(String categoryId, String topicId) {
        return TopicResponse.from(topicService.deleteById(categoryId, topicId));
    }

    public List<TopicResponse> findTopicsForUser(String userId) {
        return topicService.findTopicsForUser(userId)
                           .stream()
                           .map(TopicResponse::from)
                           .toList();
    }
}
