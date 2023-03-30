package inc.evil.aws.fiddle.topic.web;

import inc.evil.aws.fiddle.topic.domain.Topic;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopicResponse {

    private String id;
    private String name;
    private String createdAt;
    private String categoryId;

    public static TopicResponse from(Topic topic) {
        return TopicResponse.builder()
                            .id(topic.getId())
                            .name(topic.getTitle())
                            .createdAt(topic.getCreatedAt().toString())
                            .categoryId(topic.getCategoryId())
                            .build();
    }
}
