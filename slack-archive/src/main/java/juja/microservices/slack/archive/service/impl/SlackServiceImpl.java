package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.SlackRepository;
import juja.microservices.slack.archive.service.SlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class SlackServiceImpl implements SlackService {

    @Inject
    private SlackRepository slackRepository;

    @Override
    public List<RawMessage> getRawMessage(String channelId, String ts) {
        return slackRepository.getRawMessages(channelId, ts);
    }
}
