package inc.evil.aws.fiddle.user.domain;

import inc.evil.aws.fiddle.common.DynamoDbBase;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;

@DynamoDbBean
public class User extends DynamoDbBase {
    public static final String USER_PK_PREFIX = "User#";
    public static final String USER_SK_PREFIX = "User#";

    private String firstName;
    private String lastName;
    private String userName;
    private String createdAt;
    private Integer karmaPoints;

    public User() {
    }

    public User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userName = builder.userName;
        this.createdAt = builder.createdAt;
        this.karmaPoints = builder.karmaPoints;
    }

    @DynamoDbIgnore
    public String getId() {
        return getPartitionKey().substring(USER_PK_PREFIX.length());
    }

    public void setId(String id) {
        this.partitionKey = USER_PK_PREFIX + id;
        this.sortKey = USER_SK_PREFIX + id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public Integer getKarmaPoints() {
        return this.karmaPoints;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setKarmaPoints(Integer karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String firstName;
        private String lastName;
        private String userName;
        private String createdAt;
        private Integer karmaPoints;

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder karmaPoints(Integer karmaPoints) {
            this.karmaPoints = karmaPoints;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
