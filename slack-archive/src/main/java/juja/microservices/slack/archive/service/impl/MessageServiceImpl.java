package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.dto.MessagesRequest;
import juja.microservices.slack.archive.model.entity.Message;
import juja.microservices.slack.archive.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    @Override
    public List<Message> getMessages(MessagesRequest request) {
        //TODO Should be implemented
        return null;
    }

    @Override
    public void saveMessage(Message message) {
        //TODO Should be implemented
    }
}
