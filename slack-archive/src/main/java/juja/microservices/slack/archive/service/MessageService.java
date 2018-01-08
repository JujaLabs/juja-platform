package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.dto.MessagesRequest;
import juja.microservices.slack.archive.model.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> getMessages(MessagesRequest request);

    void saveMessage(Message message);
}
