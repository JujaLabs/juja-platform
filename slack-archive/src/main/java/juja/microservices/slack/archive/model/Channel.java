package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Channel {
    @Id
    private String id;
    private String channelId;
    private String channelName;
    private String ts;

    @JsonCreator
    public Channel(@JsonProperty("id") String channelId,
                   @JsonProperty("name") String channelName,
                   @JsonProperty("ts") String ts) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.ts = ts;
    }
}
