package inc.evil.aws.fiddle.dynamodb.facade;

import java.util.List;

import inc.evil.aws.fiddle.dynamodb.web.CreateMovieRequest;
import inc.evil.aws.fiddle.dynamodb.web.MovieResponse;
import inc.evil.aws.fiddle.dynamodb.domain.Movie;
import inc.evil.aws.fiddle.dynamodb.service.MovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieFacade {

    private final MovieService movieService;

    public MovieFacade(MovieService movieService) {
        this.movieService = movieService;
    }

    public List<MovieResponse> findAll() {
        return movieService.findAll()
                           .stream()
                           .map(MovieResponse::from)
                           .toList();
    }

    public MovieResponse findById(String id) {
        Movie movie = movieService.findById(id);
        return MovieResponse.from(movie);
    }

    public MovieResponse create(CreateMovieRequest createMovieRequest) {
        Movie movieToCreate = createMovieRequest.toMovie();
        Movie createdMovie = movieService.create(movieToCreate);
        return MovieResponse.from(createdMovie);
    }
}
