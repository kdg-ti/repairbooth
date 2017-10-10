package be.kdg.repaircafe.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller bean that handle all webpages that are formed by URL of form /topic?id=X
 */
@Controller
public class TopicController {

    @RequestMapping("/topic")
    public String showTopic(@RequestParam("id") String topic) {
        return topic.toLowerCase();
    }
}
