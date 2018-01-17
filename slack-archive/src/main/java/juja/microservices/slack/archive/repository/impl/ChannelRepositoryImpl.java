package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.model.entity.RawChannel;
import juja.microservices.slack.archive.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@Slf4j
public class ChannelRepositoryImpl implements ChannelRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.raw.channels.collection}")
    private String rawChannelsCollectionName;

    @Override
    public List<Channel> getChannels() {
        List<Channel> channels = mongoTemplate.findAll(Channel.class, rawChannelsCollectionName);
        if (channels.isEmpty()) {
            log.info("Channels collection is empty");
        }
        return channels;
    }

    @Override
    public void saveRawChannels(List<RawChannel> channels) {
        mongoTemplate.remove(new Query(), rawChannelsCollectionName);
        channels.stream()
                .forEach(channel -> mongoTemplate.save(channel));
    }
}
