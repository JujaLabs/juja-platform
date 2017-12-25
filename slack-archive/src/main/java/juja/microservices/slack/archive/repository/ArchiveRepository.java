package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.model.entity.Message;

import java.util.List;

public interface ArchiveRepository {

    void saveMessage(Message message);

    List<Channel> getChannels();

    void saveOrUpdateChannels(List<Channel> channels);

    List<Message> getMessages(String channel, int number);
}
