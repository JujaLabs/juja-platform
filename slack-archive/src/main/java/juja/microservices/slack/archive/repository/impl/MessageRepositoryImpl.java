package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@Slf4j
public class MessageRepositoryImpl implements MessageRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.raw.messages.collection")
    private String rawMessagesCollectionName;

    @Override
    public void saveRawMessages(List<RawMessage> messages) {
        mongoTemplate.remove(new Query(), rawMessagesCollectionName);
        messages.forEach(rawMessage -> mongoTemplate.save(rawMessage));
    }
}
