package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.Channel;

import java.util.List;

public interface ChannelRepository {

    List<Channel> getChannel();

    void saveChannel(Channel channel);
}
