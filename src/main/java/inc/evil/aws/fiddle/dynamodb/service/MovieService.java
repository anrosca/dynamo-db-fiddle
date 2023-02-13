package inc.evil.aws.fiddle.dynamodb.service;

import java.util.List;

import inc.evil.aws.fiddle.dynamodb.repository.MovieRepository;
import inc.evil.aws.fiddle.dynamodb.domain.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final IdGenerator idGenerator;

    public MovieService(MovieRepository movieRepository, IdGenerator idGenerator) {
        this.movieRepository = movieRepository;
        this.idGenerator = idGenerator;
    }

    public Movie findById(String id) {
        return movieRepository.findById(id)
                              .orElseThrow(() -> new MovieNotFoundException("No movie with id: '" + id + "' was found."));
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie create(Movie movieToCreate) {
        movieToCreate.setId(idGenerator.generate());
        return movieRepository.create(movieToCreate);
    }
}
