package inc.evil.aws.fiddle.topic.domain;

import inc.evil.aws.fiddle.common.DynamoDbBase;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;

@DynamoDbBean
public class Topic extends DynamoDbBase {
    public static final String TOPIC_PK_PREFIX = "Category#";
    public static final String TOPIC_SK_PREFIX = "Topic#";

    private String title;
    private String author;
    private String createdAt;

    public Topic() {
    }

    public Topic(TopicBuilder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.createdAt = builder.createdAt;
        this.partitionKey = TopicKeyBuilder.makePartitionKey(builder.categoryId);
    }

    @DynamoDbIgnore
    public String getId() {
        return getSortKey().substring(TOPIC_SK_PREFIX.length());
    }

    public void setId(String id) {
        this.sortKey = TopicKeyBuilder.makeSortKey(id);
    }

    public String getCategoryId() {
        return getPartitionKey().substring(TOPIC_PK_PREFIX.length());
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static TopicBuilder builder() {
        return new TopicBuilder();
    }

    public static class TopicBuilder {

        private String title;
        private String author;
        private String createdAt;
        private String categoryId;
        private String partitionKey;
        private String sortKey;

        public TopicBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TopicBuilder author(String author) {
            this.author = author;
            return this;
        }

        public TopicBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TopicBuilder categoryId(String categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public TopicBuilder partitionKey(String partitionKey) {
            this.partitionKey = partitionKey;
            return this;
        }

        public TopicBuilder sortKey(String sortKey) {
            this.sortKey = sortKey;
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
