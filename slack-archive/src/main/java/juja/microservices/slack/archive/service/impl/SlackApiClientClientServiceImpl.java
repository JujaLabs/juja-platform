package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.api.SlackApiClient;
import juja.microservices.slack.archive.service.SlackApiClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlackApiClientClientServiceImpl implements SlackApiClientService {

    private SlackApiClient slackApiClient;

    public SlackApiClientClientServiceImpl(SlackApiClient slackApiClient) {
        this.slackApiClient = slackApiClient;
    }

    @Override
    public List<ChannelDTO> receiveChannels() {
        return slackApiClient.receiveChannelsList();
    }
}
