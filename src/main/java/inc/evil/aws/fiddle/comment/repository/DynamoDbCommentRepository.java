package inc.evil.aws.fiddle.comment.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import inc.evil.aws.fiddle.comment.domain.Comment;
import inc.evil.aws.fiddle.dynamodb.config.properties.AwsProperties;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

@Repository
public class DynamoDbCommentRepository implements CommentRepository {

    private final DynamoDbTable<Comment> commentTable;
    private final DynamoDbClient dynamoDbClient;
    private final AwsProperties awsProperties;

    public DynamoDbCommentRepository(DynamoDbTable<Comment> commentTable, DynamoDbClient dynamoDbClient, AwsProperties awsProperties) {
        this.commentTable = commentTable;
        this.dynamoDbClient = dynamoDbClient;
        this.awsProperties = awsProperties;
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

    @Override
    public Optional<Comment> likeComment(String topicId, String commentId) {
        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(awsProperties.dynamoDbTableName())
                .key(Map.of(
                        "PK", AttributeValue.builder().s(Comment.CommentKeyBuilder.makePartitionKey(topicId)).build(),
                        "SK", AttributeValue.builder().s(Comment.CommentKeyBuilder.makeSortKey(commentId)).build()
                ))
                .updateExpression("SET likeCount = likeCount + :increment_amount")
                .expressionAttributeValues(Map.of(":increment_amount", AttributeValue.builder().n("1").build()))
                .build();
        UpdateItemResponse updateItemResponse = dynamoDbClient.updateItem(updateItemRequest);
        return findById(topicId, commentId);
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
