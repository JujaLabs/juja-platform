package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.api.SlackApiClient;
import juja.microservices.slack.archive.service.SlackApiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlackApiServiceImpl implements SlackApiService {

    private SlackApiClient slackApiClient;

    public SlackApiServiceImpl(SlackApiClient slackApiClient) {
        this.slackApiClient = slackApiClient;
    }

    @Override
    public List<ChannelDTO> receiveChannels() {
        return slackApiClient.receiveChannelsList();
    }
}
