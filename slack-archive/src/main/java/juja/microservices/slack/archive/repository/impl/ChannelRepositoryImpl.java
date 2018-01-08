package juja.microservices.slack.archive.repository.impl;

import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
@Slf4j
public class ChannelRepositoryImpl implements ChannelRepository {

    @Inject
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.channels.collection}")
    private String channelsCollectionName;

    @Override
    public List<Channel> getChannels() {
        List<Channel> channels = mongoTemplate.findAll(Channel.class, channelsCollectionName);
        if (channels.isEmpty()) {
            log.info("Channels collection is empty");
        }
        return channels;
    }

    @Override
    public void saveOrUpdateChannels(List<Channel> channels) {

        for (Channel channel : channels) {
            Query query = new Query();
            query.addCriteria(Criteria.where("channelId").is(channel.getChannelId()));
            Channel foundChannel = mongoTemplate.findOne(query, Channel.class, channelsCollectionName);
            if (foundChannel == null) {
                mongoTemplate.insert(channel, channelsCollectionName);
            } else {
                if (!foundChannel.getChannelName().equals(channel.getChannelName())) {
                    foundChannel.setChannelName(channel.getChannelName());
                    mongoTemplate.save(foundChannel, channelsCollectionName);
                }
            }
        }
    }
}
