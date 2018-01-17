package juja.microservices.slack.archive.sheduler;

import juja.microservices.slack.archive.api.SlackApiClient;
import juja.microservices.slack.archive.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {

    private SlackApiClient slackApiClient;
    private ChannelService channelService;

    public ScheduledTask(SlackApiClient slackApiClient, ChannelService channelService) {
        this.slackApiClient = slackApiClient;
        this.channelService = channelService;
    }

    @Scheduled(fixedRate = 43200)
    public void scheduleTaskWithFixedRate() {
        log.debug("Scheduled task - receive channel list from slack start");
        channelService.saveRawChannels(slackApiClient.receiveRawChannelsList());
        log.debug("Sheduled task receive channel list from slack finish");
    }
}
