package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@Slf4j
public class ChannelRepositoryImpl implements ChannelRepository {

    private final MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.channels.collection}")
    private String channelsCollectionName;

    @Inject
    public ChannelRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
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
    public void saveChannel(Channel channel) {
        Query query = new Query();
        query.addCriteria(new Criteria().where("channelId").is(channel.getChannelId()));
        Update update = new Update();
        update.set("channelId", channel.getChannelId());
        update.set("channelName", channel.getChannelName());
        update.set("channelTs", channel.getChannelTs());
        mongoTemplate.upsert(query, update, channelsCollectionName);
    }
}
