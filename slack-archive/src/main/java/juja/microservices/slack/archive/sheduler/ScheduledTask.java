package juja.microservices.slack.archive.sheduler;

import juja.microservices.slack.archive.service.ChannelService;
import juja.microservices.slack.archive.service.SlackApiClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {

    private SlackApiClientService slackApiClientService;
    private ChannelService channelService;

    public ScheduledTask(SlackApiClientService slackApiClientService, ChannelService channelService) {
        this.slackApiClientService = slackApiClientService;
        this.channelService = channelService;
    }

    @Scheduled(fixedRate = 43200)
    public void scheduleTaskWithFixedRate() {
        log.debug("Scheduled task - receive channel list from slack start");
        channelService.saveChannels(slackApiClientService.receiveChannels());
        log.debug("Sheduled task receive channel list from slack finish");
    }
}
