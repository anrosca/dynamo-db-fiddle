package inc.evil.aws.fiddle.dynamodb.web;

import java.util.List;

import inc.evil.aws.fiddle.dynamodb.facade.MovieFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieFacade movieFacade;

    public MovieController(MovieFacade movieFacade) {
        this.movieFacade = movieFacade;
    }

    @GetMapping
    public List<MovieResponse> findAll() {
        return movieFacade.findAll();
    }

    @GetMapping("{id}")
    public MovieResponse findById(@PathVariable String id) {
        return movieFacade.findById(id);
    }

    @PostMapping
    public MovieResponse create(@RequestBody CreateMovieRequest createMovieRequest) {
        return movieFacade.create(createMovieRequest);
    }
}
