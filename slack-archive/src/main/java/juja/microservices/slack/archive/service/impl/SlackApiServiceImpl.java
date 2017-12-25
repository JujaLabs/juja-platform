package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.api.SlackApi;
import juja.microservices.slack.archive.service.SlackApiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlackApiServiceImpl implements SlackApiService {

    SlackApi slackApi;

    public SlackApiServiceImpl(SlackApi slackApi) {
        this.slackApi = slackApi;
    }

    @Override
    public List<ChannelDTO> receiveChannels() {
        return slackApi.receiveChannelsList();
    }
}
