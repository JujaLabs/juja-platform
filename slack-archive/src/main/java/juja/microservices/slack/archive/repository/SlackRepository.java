package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.model.RawMessage;

import java.util.List;

public interface SlackRepository {

    List<RawMessage> getRawMessages(String channelId, String ts);
}
