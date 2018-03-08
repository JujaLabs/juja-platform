package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.exceptions.ArchiveException;
import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.SlackRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class SlackRepositoryImpl implements SlackRepository {

    private static final String JSON_RESPONSE_NAME_OK = "ok";
    private static final String JSON_RESPONSE_NAME_MESSAGES = "messages";
    private static final String JSON_RESPONSE_NAME_TS = "ts";
    private static final String JSON_RESPONSE_NAME_HAS_MORE = "has_more";
    private static final String JSON_RESPONSE_NAME_ERROR = "error";

    private final RestTemplate restTemplate;

    @Value("${slack.api.messages.urltemplate}")
    private  String slackApiMessagesUrlTemplate;
    @Value("${slack.api.token}")
    private  String slackApiToken;

    public SlackRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RawMessage> getRawMessages(String channelId, String ts) {

        String url = String.format(slackApiMessagesUrlTemplate, slackApiToken, channelId, ts);

        List<RawMessage> result = new LinkedList<>();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Document responseDocument = Document.parse(response.getBody());

        if (responseDocument.getBoolean(JSON_RESPONSE_NAME_OK)) {
            List<Document> listMessages = (List<Document>) responseDocument.get(JSON_RESPONSE_NAME_MESSAGES);
            do {
                for (Document rawMessage : listMessages) {
                    String id = rawMessage.getString(JSON_RESPONSE_NAME_TS);
                    Date date = getDateFromDocument(rawMessage);
                    Map<String, Object> rawMessagesContent = getMapRawMessageFromDocument(rawMessage);

                    result.add(new RawMessage(id, rawMessagesContent, channelId, date));
                }
            } while (responseDocument.getBoolean(JSON_RESPONSE_NAME_HAS_MORE));
        } else {
            throw new ArchiveException(responseDocument.getString(JSON_RESPONSE_NAME_ERROR));
        }
        return result;
    }

    private Date getDateFromDocument(Document rawMessage) {
        String timestamp = rawMessage.get(JSON_RESPONSE_NAME_TS).toString().replace(".000", "");
        return new Date(Long.parseLong(timestamp));
    }

    private Map<String, Object> getMapRawMessageFromDocument(Document rawMessage) {
        Map<String, Object> result = new HashMap<>();
        Set<String> rawMessageObjectNames = rawMessage.keySet();
        rawMessageObjectNames.forEach(key -> result.put(key, rawMessage.get(key)));
        return result;
    }
}
