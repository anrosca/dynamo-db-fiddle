package inc.evil.aws.fiddle.common;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
