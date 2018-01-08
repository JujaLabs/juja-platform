package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.entity.Channel;

import java.util.List;

public interface ChannelRepository {
    List<Channel> getChannels();

    void saveOrUpdateChannels(List<Channel> channels);
}
