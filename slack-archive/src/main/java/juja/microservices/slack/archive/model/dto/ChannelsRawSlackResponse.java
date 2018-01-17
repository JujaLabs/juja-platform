package juja.microservices.slack.archive.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelsRawSlackResponse {

    @JsonProperty("ok")
    private String ok;

    private List<Map<String, Object>> channels;

    private Map<String, Object> responseMetadata;

    public void addChannels(List<Map<String, Object>> channels) {

        if (this.channels == null) {
            channels = new LinkedList<>();
        }
        this.channels.addAll(channels);
    }
}
