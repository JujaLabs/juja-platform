package juja.microservices.slack.archive.integration;

import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class MessageRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private MessageRepository repository;

    @Before
    public void setUp() {
        repository.saveRawMessages(getRawMessageList());
    }

    @Test
    public void getRawMessagesTest() {

        Map<String, Object> rawMessageContent1 = new LinkedHashMap<>();
        rawMessageContent1.put("type", "message");
        rawMessageContent1.put("user", "user2");
        rawMessageContent1.put("text", "message test2");
        rawMessageContent1.put("ts", "2222222222.000708");

        List<RawMessage> rawMessages = repository.getRawMessages();
        RawMessage rawMessage = rawMessages.get(rawMessages.size() - 1);

        assertNotNull(rawMessages);
        assertEquals("2222222222.000708", rawMessage.getId());
        assertEquals(rawMessageContent1, rawMessage.getRawMessage());
        assertEquals("C8FU4T48P", rawMessage.getChannel());
        assertEquals(new Date(Long.parseLong("2222222222708")), rawMessage.getDate());

    }

    @Test
    public void saveRawMessageTest() {

        Map<String, Object> rawMessageContent1 = new LinkedHashMap<>();
        rawMessageContent1.put("type", "message");
        rawMessageContent1.put("user", "user3");
        rawMessageContent1.put("text", "message test3");
        rawMessageContent1.put("ts", "3333333333.000708");
        RawMessage rawMessage1 = new RawMessage("3333333333.000708", rawMessageContent1,
                "C8FU4T48P", new Date(Long.parseLong("3333333333708")));

        Map<String, Object> rawMessageContent2 = new LinkedHashMap<>();
        rawMessageContent2.put("type", "message");
        rawMessageContent2.put("user", "user4");
        rawMessageContent2.put("text", "message test4");
        rawMessageContent2.put("ts", "4444444444.000708");
        RawMessage rawMessage2 = new RawMessage("4444444444.000708", rawMessageContent2,
                "C8FU4T48P", new Date(Long.parseLong("4444444444708")));

        List<RawMessage> rawMessagesToSave = Arrays.asList(rawMessage1, rawMessage2);
        repository.saveRawMessages(rawMessagesToSave);

        List<RawMessage> actualChannelList = repository.getRawMessages();

        assertEquals(4, actualChannelList.size());
    }

    private List<RawMessage> getRawMessageList() {
        Map<String, Object> rawMessageContent1 = new LinkedHashMap<>();
        rawMessageContent1.put("type", "message");
        rawMessageContent1.put("user", "user1");
        rawMessageContent1.put("text", "message test");
        rawMessageContent1.put("ts", "1111111111.000708");
        RawMessage rawMessage1 = new RawMessage("1111111111.000708", rawMessageContent1,
                "C8FU4T48P", new Date(Long.parseLong("1111111111708")));

        Map<String, Object> rawMessageContent2 = new LinkedHashMap<>();
        rawMessageContent2.put("type", "message");
        rawMessageContent2.put("user", "user2");
        rawMessageContent2.put("text", "message test2");
        rawMessageContent2.put("ts", "2222222222.000708");
        RawMessage rawMessage2 = new RawMessage("2222222222.000708", rawMessageContent2,
                "C8FU4T48P", new Date(Long.parseLong("2222222222708")));

        return Arrays.asList(rawMessage1, rawMessage2);
    }
}
