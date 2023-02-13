package inc.evil.aws.fiddle.dynamodb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("aws")
public record AwsProperties(String endpointOverride, String region, String dynamoDbTableName, AwsCredentials credentials) {
    @ConstructorBinding
    public AwsProperties {
    }
}
