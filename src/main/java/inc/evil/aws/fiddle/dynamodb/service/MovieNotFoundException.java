package inc.evil.aws.fiddle.dynamodb.service;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String message) {
        super(message);
    }
}
