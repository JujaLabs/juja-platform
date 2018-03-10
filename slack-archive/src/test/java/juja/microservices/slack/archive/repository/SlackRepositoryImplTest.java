package juja.microservices.slack.archive.repository;

import juja.microservices.slack.archive.exceptions.ArchiveException;
import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SlackRepositoryImplTest {

    @Inject
    private RestTemplate restTemplate;
    @Inject
    private SlackRepository slackRepository;
    @Value("${slack.api.messages.urltemplate}")
    private String slackApiMessagesUrlTemplate;
    @Value("${slack.api.token}")
    private String slackApiToken;
    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void getRawMessages() {
        Map<String, Object> rawMessageMap = new LinkedHashMap<>();
        rawMessageMap.put("type", "message");
        rawMessageMap.put("user", "U8ET8UJ0L");
        rawMessageMap.put("text", "first message");
        rawMessageMap.put("ts", "1517498771.000708");
        RawMessage rawMessage1 = new RawMessage("1517498771.000708", rawMessageMap,
                "C8FU4T48P", new Date(Long.parseLong("1517498771708")));

        Map<String, Object> rawMessageMap2 = new LinkedHashMap<>();
        rawMessageMap2.put("type", "message");
        rawMessageMap2.put("user", "U8ET8QQQQ");
        rawMessageMap2.put("text", "last message");
        rawMessageMap2.put("ts", "1520599268.000174");
        RawMessage rawMessage2 = new RawMessage("1520599268.000174", rawMessageMap2,
                "C8FU4T48P", new Date(Long.parseLong("1520599268174")));

        List<RawMessage> expectedMessages = Arrays.asList(rawMessage1, rawMessage2);

        String url = String.format(slackApiMessagesUrlTemplate, slackApiToken, "C8FU4T48P", "1513275104.000059");

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET)).
                andRespond(withSuccess(Util.readStringFromFile("repository/raw_messages.json"),
                        MediaType.APPLICATION_JSON));

        List<RawMessage> result = slackRepository.getRawMessages("C8FU4T48P", "1513275104.000059");

        assertEquals(expectedMessages, result);
    }

    @Test(expected = ArchiveException.class)
    public void getRawMessagesWhenChannelDoesNotExist() {
        String url = String.format(slackApiMessagesUrlTemplate, slackApiToken, "Channel_does_not_exist", "1513275104.000059");

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET)).
                andRespond(withSuccess(Util.readStringFromFile("repository/raw_messages_channel_does_not_exist.json"),
                        MediaType.APPLICATION_JSON));
        slackRepository.getRawMessages("Channel_does_not_exist", "1513275104.000059");
    }

    @Test(expected = ArchiveException.class)
    public void getRawMessagesWhenInvalidTsOldest() {
        String url = String.format(slackApiMessagesUrlTemplate, slackApiToken, "C8FU4T48P", "invalid_ts_oldest");

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET)).
                andRespond(withSuccess(Util.readStringFromFile("repository/raw_messages_invalid_ts_oldest.json"),
                        MediaType.APPLICATION_JSON));
        slackRepository.getRawMessages("C8FU4T48P", "invalid_ts_oldest");
    }
}
