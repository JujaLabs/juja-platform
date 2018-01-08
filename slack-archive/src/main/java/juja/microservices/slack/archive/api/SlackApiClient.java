package juja.microservices.slack.archive.api;

import juja.microservices.slack.archive.model.dto.ChannelDTO;

import java.util.List;

public interface SlackApiClient {

    List<ChannelDTO> receiveChannelsList();
}
