package inc.evil.aws.fiddle.topic.service;

import java.util.List;

import inc.evil.aws.fiddle.common.IdGenerator;
import inc.evil.aws.fiddle.topic.domain.Topic;
import inc.evil.aws.fiddle.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final IdGenerator idGenerator;

    public TopicService(TopicRepository topicRepository, IdGenerator idGenerator) {
        this.topicRepository = topicRepository;
        this.idGenerator = idGenerator;
    }

    public List<Topic> findAllFor(String categoryId) {
        return topicRepository.findByCategory(categoryId);
    }

    public Topic create(Topic topicToCreate) {
        topicToCreate.setId(idGenerator.generate());
        return topicRepository.create(topicToCreate);
    }

    public Topic findById(String categoryId, String topicId) {
        return topicRepository.findByCategoryIdAndTopicId(categoryId, topicId)
                              .orElseThrow(() -> new TopicNotFoundException("No topic in category: " + categoryId + " and topicId: " + topicId + " was found"));
    }

    public Topic deleteById(String categoryId, String topicId) {
        return topicRepository.deleteByCategoryIdAndTopicId(categoryId, topicId)
                              .orElseThrow(() -> new TopicNotFoundException("No topic in category: " + categoryId + " and topicId: " + topicId + " was found"));
    }
}
