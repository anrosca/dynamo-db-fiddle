package inc.evil.aws.fiddle.dynamodb.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.dynamodb.domain.Movie;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Repository
public class DynamoDbMovieRepository implements MovieRepository {

    private final DynamoDbTable<Movie> movieTable;

    public DynamoDbMovieRepository(DynamoDbTable<Movie> movieTable) {
        this.movieTable = movieTable;
    }

    @Override
    public List<Movie> findAll() {
        return movieTable.scan()
                         .items()
                         .stream()
                         .toList();
    }

    @Override
    public Optional<Movie> findById(String id) {
        Key key = Key.builder()
                     .partitionValue(id)
                     .build();
        return Optional.of(movieTable.getItem(key));
    }

    @Override
    public Movie create(Movie movieToCreate) {
        movieTable.putItem(movieToCreate);
        return movieToCreate;
    }
}
