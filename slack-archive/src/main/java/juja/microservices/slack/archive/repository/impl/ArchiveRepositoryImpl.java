package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.model.Message;
import juja.microservices.slack.archive.repository.ArchiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@Slf4j
public class ArchiveRepositoryImpl implements ArchiveRepository {
    @Inject
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.channels.collection}")
    private String channelsCollectionName;

    @Override
    public void saveMessage(Message message) {
        //TODO Should be implemented
    }

    @Override
    public List<Channel> getChannels() {
        List<Channel> channels = mongoTemplate.findAll(Channel.class, channelsCollectionName);
        if (channels.isEmpty()) {
            log.info("Channels collection is empty");
        }
        return channels;
    }

    @Override
    public List<Message> getMessages(String channel, int number) {
        //TODO Should be implemented
        return null;
    }
}