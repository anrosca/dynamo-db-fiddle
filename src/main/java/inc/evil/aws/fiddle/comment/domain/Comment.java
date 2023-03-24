package inc.evil.aws.fiddle.comment.domain;

import inc.evil.aws.fiddle.common.DynamoDbBase;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;

@DynamoDbBean
public class Comment extends DynamoDbBase {
    public static final String COMMENT_PK_PREFIX = "Topic#";
    public static final String COMMENT_SK_PREFIX = "Comment#";

    private String author;
    private String createdAt;
    private String text;

    public Comment() {
    }

    private Comment(CommentBuilder builder) {
        this.author = builder.author;
        this.createdAt = builder.createdAt;
        this.text = builder.text;
        this.partitionKey = CommentKeyBuilder.makePartitionKey(builder.topicId);
    }

    @DynamoDbIgnore
    public String getId() {
        return sortKey.substring(COMMENT_SK_PREFIX.length());
    }

    public void setId(String id) {
        this.sortKey = CommentKeyBuilder.makeSortKey(id);
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public String getText() {
        return this.text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public String getTopicId() {
        return partitionKey.substring(COMMENT_PK_PREFIX.length());
    }

    public static class CommentBuilder {

        private String author;
        private String createdAt;
        private String text;
        private String topicId;

        public CommentBuilder author(String author) {
            this.author = author;
            return this;
        }

        public CommentBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommentBuilder text(String text) {
            this.text = text;
            return this;
        }

        public CommentBuilder topicId(String topicId) {
            this.topicId = topicId;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    public static class CommentKeyBuilder {
        public static String makePartitionKey(String id) {
            return COMMENT_PK_PREFIX + id;
        }

        public static String makeSortKey(String id) {
            return COMMENT_SK_PREFIX + id;
        }
    }
}
