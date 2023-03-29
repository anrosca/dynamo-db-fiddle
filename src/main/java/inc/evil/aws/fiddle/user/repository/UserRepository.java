package inc.evil.aws.fiddle.user.repository;

import java.util.Optional;

import inc.evil.aws.fiddle.user.domain.User;

public interface UserRepository {
    Optional<User> findById(String id);
}
