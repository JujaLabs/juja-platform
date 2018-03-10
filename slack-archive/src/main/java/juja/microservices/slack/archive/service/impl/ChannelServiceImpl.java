package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.repository.ChannelRepository;
import juja.microservices.slack.archive.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Inject
    public ChannelServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public List<Channel> getChannels() {
        return channelRepository.getChannels();
    }

    @Override
    public void saveChannel(Channel channel) {
        channelRepository.saveChannel(channel);
    }
}
