package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.Channel;

import java.util.List;

public interface ChannelService {
    List<Channel> getChannels();

    void saveChannel(Channel channel);
}
