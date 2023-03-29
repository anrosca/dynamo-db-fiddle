package inc.evil.aws.fiddle.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

public abstract class AbstractWebIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    protected <T> RequestEntity<T> makeRequestFor(String urlTemplate, HttpMethod httpMethod) {
        return makeRequestFor(urlTemplate, httpMethod, null);
    }

    protected <T> RequestEntity<T> makeRequestFor(String urlTemplate, HttpMethod httpMethod, T payload) {
        return RequestEntity.method(httpMethod, "http://localhost:{port}" + urlTemplate, port)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(payload);
    }
}
