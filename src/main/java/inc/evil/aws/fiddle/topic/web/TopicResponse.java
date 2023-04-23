package inc.evil.aws.fiddle.topic.web;

import inc.evil.aws.fiddle.topic.domain.Topic;
import inc.evil.aws.fiddle.topic.domain.TopicTag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TopicResponse {

    private String id;
    private String name;
    private String createdAt;
    private String categoryId;
    private List<TopicTag> tags;

    public static TopicResponse from(Topic topic) {
        return TopicResponse.builder()
                .id(topic.getId())
                .name(topic.getTitle())
                .createdAt(topic.getCreatedAt().toString())
                .categoryId(topic.getCategoryId())
                .tags(topic.getTags())
                .build();
    }
}
