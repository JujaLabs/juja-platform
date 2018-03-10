package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.RawMessage;

import java.util.List;

public interface MessageRepository {

    List<RawMessage> getRawMessages();

    void saveRawMessages(List<RawMessage> messages);
}
