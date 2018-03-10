package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.exceptions.ArchiveException;
import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.model.RawMessageSlackResponse;
import juja.microservices.slack.archive.repository.SlackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class SlackRepositoryImpl implements SlackRepository {

    private static final String JSON_RESPONSE_NAME_TS = "ts";

    private final RestTemplate restTemplate;

    @Value("${slack.api.messages.urltemplate}")
    private String slackApiMessagesUrlTemplate;
    @Value("${slack.api.token}")
    private String slackApiToken;

    public SlackRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RawMessage> getRawMessages(String channelId, String ts) {

        List<RawMessage> messages = new LinkedList<>();
        String url = String.format(slackApiMessagesUrlTemplate, slackApiToken, channelId, ts);

        ResponseEntity<RawMessageSlackResponse> response =
                restTemplate.getForEntity(url, RawMessageSlackResponse.class);
        RawMessageSlackResponse slackResponse = response.getBody();

        if (slackResponse.getOk()) {
            do {
                List<Map<String, Object>> receivedMessages = slackResponse.getMessages();
                for (Map<String, Object> messageContentMap : receivedMessages) {
                    String id = getTimestampFromReceiveMessage(messageContentMap);
                    Date date = getDateFromReceiveMessage(messageContentMap);
                    messages.add(new RawMessage(id, messageContentMap, channelId, date));
                }
            } while (slackResponse.getHasMore());
        } else {
            log.error("Problem with request: " + slackResponse.getError());
            throw new ArchiveException(slackResponse.getError());
        }
        log.info("Got list of raw messages from channel: " + channelId + ", from this time: " + ts);
        return sortRawMessageListByDate(messages);
    }

    private List<RawMessage> sortRawMessageListByDate(List<RawMessage> messages) {
        return messages.stream().sorted(Comparator.comparing(RawMessage::getDate)).collect(Collectors.toList());
    }

    private Date getDateFromReceiveMessage(Map<String, Object> messageContentMap) {
        String timestampInLongFormat = getTimestampFromReceiveMessage(messageContentMap).replace(".000", "");
        return new Date(Long.parseLong(timestampInLongFormat));
    }

    private String getTimestampFromReceiveMessage(Map<String, Object> messageContentMap) {
        return (String) messageContentMap.get(JSON_RESPONSE_NAME_TS);
    }
}
