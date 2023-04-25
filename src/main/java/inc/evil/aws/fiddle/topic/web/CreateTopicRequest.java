package inc.evil.aws.fiddle.topic.web;

import java.time.Instant;
import java.util.List;

import inc.evil.aws.fiddle.topic.domain.Topic;
import inc.evil.aws.fiddle.topic.domain.TopicTag;

public record CreateTopicRequest(String name, String userName, List<TopicTag> tags) {
    public Topic toTopic(String categoryId) {
        return Topic.builder()
                .title(name)
                .createdAt(Instant.now())
                .userName(userName)
                .categoryId(categoryId)
                .tags(tags)
                .build();
    }
}
