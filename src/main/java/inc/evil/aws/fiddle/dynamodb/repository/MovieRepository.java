package inc.evil.aws.fiddle.dynamodb.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.dynamodb.domain.Movie;

public interface MovieRepository {
    List<Movie> findAll();
    Optional<Movie> findById(String id);

    Movie create(Movie movieToCreate);
}
