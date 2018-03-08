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

    @Inject
    private SlackService slackService;
    @Inject
    private MessageRepository messageRepository;
    @Inject
    private ChannelService channelService;

    @Override
    public void saveRawMessages() {
        List<Channel> channels = channelService.getChannels();
        for(Channel channel: channels){
            List<RawMessage> messages = slackService.getRawMessage(channel.getChannelId(), channel.getTs());
            messageRepository.saveRawMessages(messages);
            channelService.saveChannel(channel);
        }
    }
}
