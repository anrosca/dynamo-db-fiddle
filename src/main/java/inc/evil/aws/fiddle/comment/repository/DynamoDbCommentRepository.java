package inc.evil.aws.fiddle.comment.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.comment.domain.Comment;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Repository
public class DynamoDbCommentRepository implements CommentRepository {

    private final DynamoDbTable<Comment> commentTable;

    public DynamoDbCommentRepository(DynamoDbTable<Comment> commentTable) {
        this.commentTable = commentTable;
    }

    @Override
    public List<Comment> findByTopic(String topicId) {
        QueryConditional skBeginsWithQuery = buildSkBeginsWithKeyQuery(topicId);

        QueryEnhancedRequest reverseOrderQuery = QueryEnhancedRequest
            .builder()
            .queryConditional(skBeginsWithQuery)
            .scanIndexForward(Boolean.FALSE)
            .build();

        return commentTable.query(reverseOrderQuery)
                           .items()
                           .stream()
                           .toList();
    }

    @Override
    public Comment create(Comment commentToCreate) {
        commentTable.putItem(commentToCreate);
        return commentToCreate;
    }

    @Override
    public Optional<Comment> findByTopicIdAndCommentId(String topicId, String commentId) {
        Key key = Key.builder()
                     .partitionValue(Comment.CommentKeyBuilder.makePartitionKey(topicId))
                     .sortValue(Comment.CommentKeyBuilder.makeSortKey(commentId))
                     .build();
        return Optional.ofNullable(commentTable.getItem(key));
    }

    @Override
    public Optional<Comment> deleteByTopicIdAndCommentId(String topicId, String commentId) {
        Key key = Key.builder()
                     .partitionValue(Comment.CommentKeyBuilder.makePartitionKey(topicId))
                     .sortValue(Comment.CommentKeyBuilder.makeSortKey(commentId))
                     .build();
        return Optional.ofNullable(commentTable.deleteItem(key));
    }

    @Override
    public Optional<Comment> findById(String topicId, String commentId) {
        Key key = Key.builder()
                     .partitionValue(Comment.CommentKeyBuilder.makePartitionKey(topicId))
                     .sortValue(Comment.CommentKeyBuilder.makeSortKey(commentId))
                     .build();
        return Optional.ofNullable(commentTable.getItem(key));
    }

    @Override
    public Optional<Comment> deleteById(String topicId, String commentId) {
        Key key = Key.builder()
                     .partitionValue(Comment.CommentKeyBuilder.makePartitionKey(topicId))
                     .sortValue(Comment.CommentKeyBuilder.makeSortKey(commentId))
                     .build();
        return Optional.ofNullable(commentTable.deleteItem(key));
    }

    private QueryConditional buildSkBeginsWithKeyQuery(String topicId) {
        return QueryConditional.sortBeginsWith(
            Key.builder()
               .partitionValue(Comment.CommentKeyBuilder.makePartitionKey(topicId))
               .sortValue(Comment.COMMENT_SK_PREFIX)
               .build()
        );
    }
}
