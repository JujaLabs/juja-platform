package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@Slf4j
public class MessageRepositoryImpl implements MessageRepository {

    private final MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.raw.messages.collection")
    private String rawMessagesCollectionName;

    @Inject
    public MessageRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<RawMessage> getRawMessages() {
        List<RawMessage> rawMessages = mongoTemplate.findAll(RawMessage.class, rawMessagesCollectionName);
        if (rawMessages.isEmpty()) {
            log.info("Raw_messages collection is empty");
        }
        return rawMessages;
    }

    @Override
    public void saveRawMessages(List<RawMessage> messages) {
        messages.forEach(message -> mongoTemplate.save(message, rawMessagesCollectionName));
    }
}
