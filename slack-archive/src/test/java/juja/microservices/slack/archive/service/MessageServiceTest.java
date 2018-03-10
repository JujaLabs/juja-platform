package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.MessageRepository;
import juja.microservices.slack.archive.service.impl.MessageServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    private MessageService messageService;

    @Rule
    final public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private  SlackService slackService;
    @Mock
    private  MessageRepository messageRepository;
    @Mock
    private  ChannelService channelService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        messageService = new MessageServiceImpl(slackService, messageRepository, channelService);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(slackService);
        assertNotNull(messageRepository);
        assertNotNull(channelService);
    }

    @Test
    public void saveRawMessagesTest() {
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel("CHANONEID", "flood", "1517498771.000708"));

        Map<String, Object> rawMessageMapContent1 = new LinkedHashMap<>();
        rawMessageMapContent1.put("type", "message");
        rawMessageMapContent1.put("user", "U8ET8UJ0L");
        rawMessageMapContent1.put("text", "first message");
        rawMessageMapContent1.put("ts", "1517498771.000708");
        RawMessage rawMessage1 = new RawMessage("2517498661.000708", rawMessageMapContent1,
                "CHANONEID", new Date(Long.parseLong("1517498771708")));

        Map<String, Object> rawMessageMapContent2 = new LinkedHashMap<>();
        rawMessageMapContent2.put("type", "message");
        rawMessageMapContent2.put("user", "U8ET8QQQQ");
        rawMessageMapContent2.put("text", "last message");
        rawMessageMapContent2.put("ts", "1520599268.000174");
        RawMessage rawMessage2 = new RawMessage("2520511168.000174", rawMessageMapContent2,
                "CHANTWOID", new Date(Long.parseLong("1520599268174")));
        List<RawMessage> rawMessages = Arrays.asList(rawMessage1, rawMessage2);

        when(channelService.getChannels()).thenReturn(channels);
        when(slackService.getRawMessage("CHANONEID", "1517498771.000708")).thenReturn(rawMessages);

        messageService.saveRawMessages();

        verify(channelService).getChannels();
        verify(slackService).getRawMessage("CHANONEID", "1517498771.000708");
        verify(messageRepository).saveRawMessages(rawMessages);
        verify(channelService).saveChannel(channels.get(0));
    }
}
