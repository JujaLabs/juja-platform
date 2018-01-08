package juja.microservices.slack.archive.api;

import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.utils.Util;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SlackApiClientImplTest {

    @Inject
    private RestTemplate restTemplate;
    @Inject
    private SlackApiClient slackApiClient;
    private MockRestServiceServer mockServer;
    @Value("${slack.api.token}")
    private String slackApiToken;
    @Value("${slack.api.channels.urltemplate}")
    private String slackApiChannelsUrlTemplate;

    @Before
    public void setUp() throws Exception {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void receiveChannelsList() throws Exception {

        List<ChannelDTO> expectedChannels = Arrays.asList(
                new ChannelDTO("C4EHT50DC", "commands"),
                new ChannelDTO("C4D4H0ZCY", "general"),
                new ChannelDTO("C4D4H0ZEU", "random"));

       String expectedUrlTemplate = slackApiChannelsUrlTemplate + slackApiToken;
       mockServer.expect(requestTo(expectedUrlTemplate))
               .andExpect(method(HttpMethod.GET))
               .andRespond(withSuccess(Util.readStringFromFile("api/channels.json"), MediaType.APPLICATION_JSON));

        List<ChannelDTO> result = slackApiClient.receiveChannelsList();

        assertEquals(expectedChannels, result);
    }
}