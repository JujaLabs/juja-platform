package juja.microservices.slack.archive.sheduler;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.service.ArchiveService;
import juja.microservices.slack.archive.service.SlackApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledTaskTest {

    @Mock
    private SlackApiService slackApiService;

    @Mock
    private ArchiveService archiveService;

    @Captor
    private ArgumentCaptor<List<ChannelDTO>> channelsDTOCaptor;

    private ScheduledTask scheduledTask;

    @Before
    public void setUp() throws Exception {
        scheduledTask = new ScheduledTask(slackApiService, archiveService);
    }

    @Test
    public void scheduleTaskWithFixedRate() throws Exception {

        List<ChannelDTO> channelsDTO = new ArrayList<>();
        channelsDTO.add(new ChannelDTO("CHANONEID", "flood"));
        channelsDTO.add(new ChannelDTO("CHANTWOID", "feedback"));

        when(slackApiService.receiveChannels()).thenReturn(channelsDTO);

        scheduledTask.scheduleTaskWithFixedRate();

        verify(archiveService, times(1)).saveChannels(channelsDTOCaptor.capture());
        verify(slackApiService, times(1)).receiveChannels();
        assertEquals(channelsDTO, channelsDTOCaptor.getValue());
    }

}