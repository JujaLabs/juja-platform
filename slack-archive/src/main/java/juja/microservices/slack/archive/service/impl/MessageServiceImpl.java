package juja.microservices.slack.archive.service.impl;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.MessageRepository;
import juja.microservices.slack.archive.service.ChannelService;
import juja.microservices.slack.archive.service.MessageService;
import juja.microservices.slack.archive.service.SlackService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final SlackService slackService;
    private final MessageRepository messageRepository;
    private final ChannelService channelService;

    @Inject
    public MessageServiceImpl(SlackService slackService, MessageRepository messageRepository, ChannelService channelService) {
        this.slackService = slackService;
        this.messageRepository = messageRepository;
        this.channelService = channelService;
    }

    @Override
    public void saveRawMessages() {
        List<Channel> channels = channelService.getChannels();
        for(Channel channel: channels){
            List<RawMessage> messages = slackService.getRawMessage(channel.getChannelId(), channel.getChannelTs());
            messageRepository.saveRawMessages(messages);
            channelService.saveChannel(channel);
        }
    }
}
