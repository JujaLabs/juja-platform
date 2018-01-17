package juja.microservices.slack.archive.api;

import juja.microservices.slack.archive.model.entity.RawChannel;

import java.util.List;

public interface SlackApiClient {

    List<RawChannel> receiveRawChannelsList();
}
