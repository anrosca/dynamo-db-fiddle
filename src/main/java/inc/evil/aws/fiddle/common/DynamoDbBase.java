package inc.evil.aws.fiddle.common;

import inc.evil.aws.fiddle.dynamodb.config.IndexNames;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
public class DynamoDbBase {
    protected String partitionKey;
    protected String sortKey;
    protected String gsi1PartitionKey;
    protected String gsi1SortKey;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPartitionKey() {
        return partitionKey;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSortKey() {
        return sortKey;
    }

    @DynamoDbAttribute("GSI1PK")
    @DynamoDbSecondaryPartitionKey(indexNames = IndexNames.GS1_INDEX_NAME)
    public String getGsi1PartitionKey() {
        return gsi1PartitionKey;
    }

    @DynamoDbAttribute("GSI1SK")
    @DynamoDbSecondarySortKey(indexNames = IndexNames.GS1_INDEX_NAME)
    public String getGsi1SortKey() {
        return gsi1SortKey;
    }
}
