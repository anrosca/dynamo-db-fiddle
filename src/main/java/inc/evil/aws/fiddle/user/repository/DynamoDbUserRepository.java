package inc.evil.aws.fiddle.user.repository;

import java.util.Optional;

import inc.evil.aws.fiddle.user.domain.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Repository
public class DynamoDbUserRepository implements UserRepository {

    private final DynamoDbTable<User> userDynamoDbTable;

    public DynamoDbUserRepository(DynamoDbTable<User> userTable) {
        this.userDynamoDbTable = userTable;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }
}
