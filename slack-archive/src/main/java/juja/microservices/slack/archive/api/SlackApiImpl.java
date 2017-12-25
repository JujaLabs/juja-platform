package juja.microservices.slack.archive.api;

import juja.microservices.slack.archive.exceptions.ArchiveException;
import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.model.dto.ChannelSlackResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
@Slf4j
public class SlackApiImpl implements SlackApi {

    private final RestTemplate restTemplate;

    @Value("${slack.api.token}")
    private String slackApiToken;
    @Value("${slack.api.channels.urltemplate}")
    private String slackApiChannelsUrlTemplate;

    public SlackApiImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ChannelDTO> receiveChannelsList() {
        String urlTemplate = slackApiChannelsUrlTemplate + slackApiToken;
        List<ChannelDTO> result;
        log.debug("Started request to slack api. Get channels list.");
        try {
            ResponseEntity<ChannelSlackResponse> response =
                    this.restTemplate.getForEntity(urlTemplate, ChannelSlackResponse.class);
            result = response.getBody().getChannels();
        } catch (HttpClientErrorException ex) {
            throw new ArchiveException(ex.getMessage());
        }
        log.debug("Received channels : {}", result);
        return result;
    }
}
