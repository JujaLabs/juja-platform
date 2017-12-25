package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.model.dto.MessagesRequest;
import juja.microservices.slack.archive.model.entity.Message;

import java.util.List;

public interface ArchiveService {

    void saveChannels(List<ChannelDTO> channelsDTO);

    List<Message> getMessages(MessagesRequest request);

    List<ChannelDTO> getChannels();

    void saveMessage(Message message);
}
