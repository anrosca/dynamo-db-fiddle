package inc.evil.aws.fiddle.dynamodb.web;

import inc.evil.aws.fiddle.dynamodb.domain.Movie;

public record CreateMovieRequest(String title, int publishYear) {

    public Movie toMovie() {
        return Movie.builder()
                    .title(title)
                    .publishYear(publishYear)
                    .build();
    }
}
