package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.model.entity.RawChannel;
import juja.microservices.slack.archive.repository.ChannelRepository;
import juja.microservices.slack.archive.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository repository;

    @Inject
    public ChannelServiceImpl(ChannelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveRawChannels(List<RawChannel> channels) {
        repository.saveRawChannels(channels);
    }

    @Override
    public List<ChannelDTO> getChannels() {
        List<ChannelDTO> channels = repository.getChannels().stream()
                .map(this::convertChannelToChannelDTO)
                .collect(Collectors.toList());
        log.debug("returned list of channels: {}", channels.toString());
        return channels;
    }

    private ChannelDTO convertChannelToChannelDTO(Channel channel) {
        return new ChannelDTO(channel.getChannelId(), channel.getChannelName());
    }

    private Channel convertChannelDTOToChannel(ChannelDTO channelDTO) {
        Channel result = new Channel();
        result.setChannelName(channelDTO.getName());
        result.setChannelId(channelDTO.getChannelId());
        return result;
    }
}
