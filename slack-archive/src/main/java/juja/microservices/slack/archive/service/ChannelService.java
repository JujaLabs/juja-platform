package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.model.entity.RawChannel;

import java.util.List;

public interface ChannelService {

    void saveRawChannels(List<RawChannel> channels);

    List<ChannelDTO> getChannels();
}
