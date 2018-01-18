package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.entity.Message;

import java.util.List;

public interface MessageRepository {

    void saveMessage(Message message);

    List<Message> getMessages(String channel, int number);
}
