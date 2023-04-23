package inc.evil.aws.fiddle.topic.domain;

import java.time.Instant;
import java.util.List;

import inc.evil.aws.fiddle.common.DynamoDbBase;
import lombok.EqualsAndHashCode;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;

@DynamoDbBean
@EqualsAndHashCode(callSuper = true)
public class Topic extends DynamoDbBase {
    public static final String TOPIC_PK_PREFIX = "Category#";
    public static final String TOPIC_SK_PREFIX = "Topic#";
    public static final String USER_GSI1_PK_PREFIX = "User#";

    private String title;
    private String userId;
    private Instant createdAt;
    private List<TopicTag> tags;

    public Topic() {
    }

    public Topic(TopicBuilder builder) {
        this.title = builder.title;
        this.userId = builder.userId;
        this.createdAt = builder.createdAt;
        this.tags = builder.tags;
        this.partitionKey = builder.partitionKey;
        this.sortKey = builder.sortKey;
        this.gsi1SortKey = builder.gsi1SortKey;
        this.gsi1PartitionKey = builder.gsi1PartitionKey;
    }

    @DynamoDbIgnore
    public String getId() {
        return getSortKey().substring(TOPIC_SK_PREFIX.length());
    }

    public void setId(String id) {
        this.sortKey = TopicKeyBuilder.makeSortKey(id);
        this.gsi1SortKey = TopicKeyBuilder.makeSortKey(id);
    }

    public String getCategoryId() {
        return getPartitionKey().substring(TOPIC_PK_PREFIX.length());
    }

    public String getTitle() {
        return this.title;
    }

    public String getUserId() {
        return this.userId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public List<TopicTag> getTags() {
        return tags;
    }

    public void setTags(List<TopicTag> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static TopicBuilder builder() {
        return new TopicBuilder();
    }

    public static class TopicBuilder {

        private String title;
        private String userId;
        private Instant createdAt;
        private String partitionKey;
        private String sortKey;
        private String gsi1PartitionKey;
        private String gsi1SortKey;
        private List<TopicTag> tags;

        public TopicBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TopicBuilder userId(String userId) {
            this.userId = userId;
            this.gsi1PartitionKey = USER_GSI1_PK_PREFIX + userId;
            return this;
        }

        public TopicBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TopicBuilder tags(List<TopicTag> tags) {
            this.tags = tags;
            return this;
        }

        public TopicBuilder gsi1PartitionKey(String gsi1PartitionKey) {
            this.gsi1PartitionKey = gsi1PartitionKey;
            return this;
        }

        public TopicBuilder gsi1SortKey(String gsi1SortKey) {
            this.gsi1SortKey = gsi1SortKey;
            return this;
        }

        public TopicBuilder partitionKey(String partitionKey) {
            this.partitionKey = partitionKey;
            return this;
        }

        public TopicBuilder sortKey(String sortKey) {
            this.sortKey = sortKey;
            this.gsi1SortKey = sortKey;
            return this;
        }

        public TopicBuilder categoryId(String categoryId) {
            this.partitionKey = TopicKeyBuilder.makePartitionKey(categoryId);
            return this;
        }

        public Topic build() {
            return new Topic(this);
        }
    }

    public static class TopicKeyBuilder {
        public static String makePartitionKey(String id) {
            return TOPIC_PK_PREFIX + id;
        }

        public static String makeSortKey(String id) {
            return TOPIC_SK_PREFIX + id;
        }
    }
}
