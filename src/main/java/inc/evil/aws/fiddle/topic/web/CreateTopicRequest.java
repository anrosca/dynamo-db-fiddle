package inc.evil.aws.fiddle.topic.web;

import java.time.Instant;

import inc.evil.aws.fiddle.topic.domain.Topic;

public record CreateTopicRequest(String name, String author) {
    public Topic toTopic(String categoryId) {
        return Topic.builder()
                    .title(name)
                    .createdAt(Instant.now().toString())
                    .author(author)
                    .categoryId(categoryId)
                    .build();
    }
}
