package juja.microservices.slack.archive.sheduler;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.service.ChannelService;
import juja.microservices.slack.archive.service.SlackApiClientService;
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
    private SlackApiClientService slackApiClientService;

    @Mock
    private ChannelService channelService;

    @Captor
    private ArgumentCaptor<List<ChannelDTO>> channelsDTOCaptor;

    private ScheduledTask scheduledTask;

    @Before
    public void setUp() throws Exception {
        scheduledTask = new ScheduledTask(slackApiClientService, channelService);
    }

    @Test
    public void scheduleTaskWithFixedRate() throws Exception {

        List<ChannelDTO> channelsDTO = new ArrayList<>();
        channelsDTO.add(new ChannelDTO("CHANONEID", "flood"));
        channelsDTO.add(new ChannelDTO("CHANTWOID", "feedback"));

        when(slackApiClientService.receiveChannels()).thenReturn(channelsDTO);

        scheduledTask.scheduleTaskWithFixedRate();

        verify(channelService, times(1)).saveChannels(channelsDTOCaptor.capture());
        verify(slackApiClientService, times(1)).receiveChannels();
        assertEquals(channelsDTO, channelsDTOCaptor.getValue());
    }

}