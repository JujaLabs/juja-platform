package juja.microservices.slack.archive.controller;

import juja.microservices.slack.archive.model.MessagesRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vadim Dyachenko
 */
@RestController
@RequestMapping(value = "/v1/slack-archive", produces = "application/json")
@Slf4j
public class ArchiveController {

    @PostMapping(value = "/messages/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void archiveSlackMessage(@RequestParam("token") String token,
                                    @RequestParam("text") String text,
                                    @RequestParam("response_url") String responseUrl,
                                    HttpServletResponse response) throws IOException {
        log.info("First logger test message");
        //TODO Should be implemented
    }

    @GetMapping(value = "/channels", consumes = "application/json")
    public ResponseEntity<?> getChannels() {
        //TODO Should be implemented
        return null;
    }

    @GetMapping(value = "/messages", consumes = "application/json")
    public ResponseEntity<?> getMessages(@RequestBody MessagesRequest request) {
        //TODO Should be implemented
        return null;
    }
}