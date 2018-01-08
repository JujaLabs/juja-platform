package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.entity.Message;
import juja.microservices.slack.archive.repository.MessageRepository;

import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {

    @Override
    public void saveMessage(Message message) {
        //TODO Should be implemented
    }


    @Override
    public List<Message> getMessages(String channel, int number) {
        //TODO Should be implemented
        return null;
    }
}
