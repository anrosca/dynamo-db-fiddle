package inc.evil.aws.fiddle.topic.web;

import inc.evil.aws.fiddle.topic.domain.Topic;
import lombok.Builder;

@Builder
public record TopicResponse(String id, String name, String createdAt, String categoryId) {

    public static TopicResponse from(Topic topic) {
        return TopicResponse.builder()
                            .id(topic.getId())
                            .name(topic.getTitle())
                            .createdAt(topic.getCreatedAt())
                            .categoryId(topic.getCategoryId())
                            .build();
    }
}
