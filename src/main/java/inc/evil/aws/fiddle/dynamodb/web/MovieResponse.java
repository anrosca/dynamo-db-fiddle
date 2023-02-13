package inc.evil.aws.fiddle.dynamodb.web;

import inc.evil.aws.fiddle.dynamodb.domain.Movie;
import lombok.Builder;

@Builder
public record MovieResponse(String id, String title, int publishYear) {

    public static MovieResponse from(Movie movie) {
        return MovieResponse.builder()
                            .id(movie.getId())
                            .title(movie.getTitle())
                            .publishYear(movie.getPublishYear())
                            .build();
    }
}
