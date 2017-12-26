package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.model.entity.Message;
import juja.microservices.slack.archive.model.dto.MessagesRequest;
import juja.microservices.slack.archive.repository.ArchiveRepository;
import juja.microservices.slack.archive.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveRepository repository;

    @Inject
    public ArchiveServiceImpl(ArchiveRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveChannels(List<ChannelDTO> channelsDTO) {
        repository.saveOrUpdateChannels(channelsDTO.stream()
                .map(channelDTO -> convertChannelDTOToChannel(channelDTO))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Message> getMessages(MessagesRequest request) {
        //TODO Should be implemented
        return null;
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

    @Override
    public void saveMessage(Message message) {
        //TODO Should be implemented
    }
}
