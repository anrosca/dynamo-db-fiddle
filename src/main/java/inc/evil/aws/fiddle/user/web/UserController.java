package inc.evil.aws.fiddle.user.web;

import java.util.List;

import inc.evil.aws.fiddle.topic.facade.TopicFacade;
import inc.evil.aws.fiddle.topic.web.TopicResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final TopicFacade topicFacade;

    public UserController(TopicFacade topicFacade) {
        this.topicFacade = topicFacade;
    }

    @GetMapping("{userName}/topics")
    public List<TopicResponse> findTopicsForUser(@PathVariable String userName) {
        return topicFacade.findTopicsForUser(userName);
    }
}
