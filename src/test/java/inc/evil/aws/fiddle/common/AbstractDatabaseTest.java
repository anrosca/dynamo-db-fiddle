package inc.evil.aws.fiddle.common;

import java.io.IOException;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractDatabaseTest {

    private static final String TABLE_NAME = "forum";
    private static final String GSI1_NAME = "forum-gsi";

    private static final Network network = Network.newNetwork();

    public static final LocalStackContainer localstack =
        new LocalStackContainer(DockerImageName.parse("localstack/localstack:1.4"))
            .withServices(LocalStackContainer.Service.DYNAMODB)
            .withEnv("DOCKER_HOST", "unix:///var/run/docker.sock")
            .withNetwork(network);

    @DynamicPropertySource
    public static void replaceProperties(DynamicPropertyRegistry registry) {
        registry.add("aws.endpoint-override", () -> localstack.getEndpointOverride(LocalStackContainer.Service.DYNAMODB));
        registry.add("aws.credentials.secret-key", localstack::getSecretKey);
        registry.add("aws.credentials.access-key", localstack::getAccessKey);
        registry.add("aws.region", localstack::getRegion);
    }

    static {
        localstack.start();
        createResources();
    }

    private static void createResources() {
        try {
            localstack.execInContainer("awslocal", "dynamodb", "create-table", "--table-name", TABLE_NAME,
                "--attribute-definitions", "AttributeName=PK,AttributeType=S", "AttributeName=SK,AttributeType=S",
                "AttributeName=GSI1PK,AttributeType=S", "AttributeName=GSI1SK,AttributeType=S",
                "--key-schema", "AttributeName=PK,KeyType=HASH", "AttributeName=SK,KeyType=RANGE",
                "--global-secondary-indexes", "IndexName=" + GSI1_NAME + ",KeySchema=[{AttributeName=GSI1PK,KeyType=HASH},{AttributeName=GSI1SK,KeyType=RANGE}],"
                    + "Projection={ProjectionType=ALL},ProvisionedThroughput={ReadCapacityUnits=5,WriteCapacityUnits=10}",
                "--provisioned-throughput", "ReadCapacityUnits=5,WriteCapacityUnits=5");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
