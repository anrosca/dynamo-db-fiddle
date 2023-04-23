package inc.evil.aws.fiddle.comment.domain;

import java.time.Instant;

import inc.evil.aws.fiddle.common.DynamoDbBase;
import inc.evil.aws.fiddle.user.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;

@DynamoDbBean
public class Comment extends DynamoDbBase {
    public static final String COMMENT_PK_PREFIX = "Topic#";
    public static final String COMMENT_SK_PREFIX = "Comment#";

    private String userId;
    private Instant createdAt;
    private String text;
    private long likeCount;

    public Comment() {
    }

    private Comment(CommentBuilder builder) {
        this.userId = builder.userId;
        this.createdAt = builder.createdAt;
        this.text = builder.text;
        this.likeCount = builder.likeCount;
        this.partitionKey = CommentKeyBuilder.makePartitionKey(builder.topicId);
        this.sortKey = builder.id != null ? CommentKeyBuilder.makeSortKey(builder.id) : null;
        this.gsi1PartitionKey = User.USER_PK_PREFIX + userId;
        this.gsi1SortKey = builder.id != null ? CommentKeyBuilder.makeSortKey(builder.id) : null;
    }

    @DynamoDbIgnore
    public String getId() {
        return sortKey.substring(COMMENT_SK_PREFIX.length());
    }

    public void setId(String id) {
        this.sortKey = CommentKeyBuilder.makeSortKey(id);
        this.gsi1SortKey = CommentKeyBuilder.makeSortKey(id);
    }

    public String getUserId() {
        return this.userId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public String getText() {
        return this.text;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public String getTopicId() {
        return partitionKey.substring(COMMENT_PK_PREFIX.length());
    }

    public static class CommentBuilder {

        private String userId;
        private Instant createdAt;
        private String text;
        private String topicId;
        private String id;
        private long likeCount;

        public CommentBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public CommentBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommentBuilder text(String text) {
            this.text = text;
            return this;
        }

        public CommentBuilder likeCount(long likeCount) {
            this.likeCount = likeCount;
            return this;
        }

        public CommentBuilder topicId(String topicId) {
            this.topicId = topicId;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }

        public CommentBuilder id(String id) {
            this.id = id;
            return this;
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
