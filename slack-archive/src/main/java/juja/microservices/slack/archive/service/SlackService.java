package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.RawMessage;

import java.util.List;

public interface SlackService {

    List<RawMessage> getRawMessage(String channelId, String ts);
}
