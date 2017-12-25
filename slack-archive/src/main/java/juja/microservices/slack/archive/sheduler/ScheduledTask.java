package juja.microservices.slack.archive.sheduler;

import juja.microservices.slack.archive.service.ArchiveService;
import juja.microservices.slack.archive.service.SlackApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {

    private SlackApiService slackApiService;
    private ArchiveService archiveService;

    public ScheduledTask(SlackApiService slackApiService, ArchiveService archiveService) {
        this.slackApiService = slackApiService;
        this.archiveService = archiveService;
    }

    @Scheduled(fixedRate = 43200)
    public void scheduleTaskWithFixedRate() {
        log.debug("Scheduled task - receive channel list from slack start");
        archiveService.saveChannels(slackApiService.receiveChannels());
        log.debug("Sheduled task receive channel list from slack finish");
    }
}
