package inc.evil.aws.fiddle.dynamodb.config;

import java.net.URI;

import inc.evil.aws.fiddle.category.domain.Category;
import inc.evil.aws.fiddle.comment.domain.Comment;
import inc.evil.aws.fiddle.dynamodb.config.properties.AwsProperties;
import inc.evil.aws.fiddle.topic.domain.Topic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AwsProperties.class)
public class DynamoDbConfig {

    @Bean
    public DynamoDbClient dynamoDbClient(AwsProperties properties, AwsBasicCredentials awsCredentials) {
        var builder = DynamoDbClient.builder()
                                    .region(Region.of(properties.region()));
        if (properties.endpointOverride() != null) {
            builder.endpointOverride(URI.create(properties.endpointOverride()));
        }
        return builder.credentialsProvider(() -> awsCredentials).build();
    }

    @Bean
    public AwsBasicCredentials awsCredentials(AwsProperties properties) {
        return AwsBasicCredentials.create(properties.credentials().accessKey(), properties.credentials().secretKey());
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                                     .dynamoDbClient(dynamoDbClient)
                                     .build();
    }

    @Bean
    public DynamoDbTable<Category> categoryTable(DynamoDbEnhancedClient dynamoDbEnhancedClient, AwsProperties properties) {
        return dynamoDbEnhancedClient.table(properties.dynamoDbTableName(), TableSchema.fromBean(Category.class));
    }

    @Bean
    public DynamoDbTable<Topic> topicTable(DynamoDbEnhancedClient dynamoDbEnhancedClient, AwsProperties properties) {
        return dynamoDbEnhancedClient.table(properties.dynamoDbTableName(), TableSchema.fromBean(Topic.class));
    }

    @Bean
    public DynamoDbTable<Comment> commentTable(DynamoDbEnhancedClient dynamoDbEnhancedClient, AwsProperties properties) {
        return dynamoDbEnhancedClient.table(properties.dynamoDbTableName(), TableSchema.fromBean(Comment.class));
    }
}
