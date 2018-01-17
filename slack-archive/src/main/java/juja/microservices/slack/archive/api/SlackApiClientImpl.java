package juja.microservices.slack.archive.api;

import juja.microservices.slack.archive.exceptions.ArchiveException;
import juja.microservices.slack.archive.model.dto.ChannelsRawSlackResponse;
import juja.microservices.slack.archive.model.entity.RawChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class SlackApiClientImpl implements SlackApiClient {

    private final RestTemplate restTemplate;

    @Value("${slack.api.token}")
    private String slackApiToken;
    @Value("${slack.api.channels.urltemplate}")
    private String slackApiChannelsUrlTemplate;

    public SlackApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RawChannel> receiveRawChannelsList() {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(slackApiChannelsUrlTemplate)
                .queryParam("token", slackApiToken);

        List<RawChannel> result = new LinkedList<>();
        log.debug("Started request to slack api. Get channels list.");

        try {
            String cursor = "";
            do {
                ResponseEntity<ChannelsRawSlackResponse> response =
                        this.restTemplate.getForEntity(builder.toUriString(), ChannelsRawSlackResponse.class);
                ChannelsRawSlackResponse slackResponse = response.getBody();

                List<RawChannel> receivedChannels = slackResponse.getChannels()
                        .stream()
                        .map(channel -> new RawChannel(channel))
                        .collect(Collectors.toList());

                result.addAll(receivedChannels);

                if (slackResponse.getResponseMetadata() != null && slackResponse.getResponseMetadata().get("cursor") != null) {
                    cursor = (String) slackResponse.getResponseMetadata().get("cursor");
                }
                builder.queryParam("cursor", cursor);

            } while (!cursor.equals(""));
        } catch (HttpClientErrorException ex) {
            throw new ArchiveException(ex.getMessage());
        }

        log.debug("Received channels : {}", result);
        return result;
    }
}
