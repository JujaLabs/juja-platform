package juja.microservices.links.slackbot.controller;

import juja.microservices.links.slackbot.exceptions.ExceptionsHandler;
import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.service.LinksSlackbotService;
import juja.microservices.links.utils.TestUtils;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ivan Shapovalov
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@TestPropertySource(value = {"classpath:application.properties", "classpath:messages/message.properties"})
public class LinksSlackbotControllerTest {

    @Value("${slack.slashCommandToken}")
    private String slackToken;
    @Value("${message.sorry}")
    private String messageSorry;
    @Value("${message.save.link.instant}")
    private String messageSaveLinkInstant;
    @Value("${message.save.link.delayed}")
    private String messageSaveLinkDelayed;

    private String linksSlackbotSaveLinkUrl = "/v1/commands/links/save";

    @Inject
    private MockMvc mvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private LinksSlackbotService linksSlackbotService;
    @MockBean
    private ExceptionsHandler exceptionsHandler;

    @Test
    public void onReceiveAllSlashCommandsWhenIncorrectTokenShouldReturnSorryMessage() throws Exception {
        //given
        String commandText = "http://url.com";
        String responseUrl = "http://example.com";

        //when
        try {
            mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(linksSlackbotSaveLinkUrl),
                    TestUtils.getUriVars("wrongSlackToken", "/command", commandText, responseUrl))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andExpect(content().string(messageSorry));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        verify(exceptionsHandler).setResponseUrl(responseUrl);
        verifyNoMoreInteractions(linksSlackbotService, exceptionsHandler);
    }

    @Test
    public void onReceiveAllSlashCommandsWhenAnyParamIsEmptylShouldReturnSorryMessage() throws Exception {
        //given
        String commandText = "http://url.com";
        String responseUrl = "";
        try {
            mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(linksSlackbotSaveLinkUrl),
                    TestUtils.getUriVars("wrongSlackToken", "/command", commandText, responseUrl))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andExpect(content().string(messageSorry));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        verify(exceptionsHandler).setResponseUrl(responseUrl);
        verifyNoMoreInteractions(linksSlackbotService, exceptionsHandler);
    }

    @Test
    public void onReceiveSlashCommandSaveLinkWhenAllCorrectShouldReturnOkMessage() throws Exception {
        //given
        String commandText = " <http://mail.com> ";
        Link link = new Link("id1", "http://mail.com");
        String responseUrl = "http://example.com";

        when(linksSlackbotService.saveLink(commandText)).thenReturn(link);

        //when
        mvc.perform(MockMvcRequestBuilders.post(TestUtils.getUrlTemplate(linksSlackbotSaveLinkUrl),
                TestUtils.getUriVars("slashCommandToken", "/command", commandText, responseUrl))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string(messageSaveLinkInstant));

        //then
        verify(exceptionsHandler).setResponseUrl(responseUrl);
        verify(linksSlackbotService).saveLink(commandText);
        ArgumentCaptor<RichMessage> captor = ArgumentCaptor.forClass(RichMessage.class);
        verify(restTemplate).postForObject(eq(responseUrl), captor.capture(), eq(String.class));
        assertTrue(captor.getValue().getText().contains(String.format(messageSaveLinkDelayed, link.getUrl())));
        verifyNoMoreInteractions(linksSlackbotService, exceptionsHandler, restTemplate);
    }
}
