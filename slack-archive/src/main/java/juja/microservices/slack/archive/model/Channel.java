package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@EqualsAndHashCode
@ToString
public class Channel {
    @Id
    private String id;
    private String channelId;
    private String channelName;
    private String channelTs;

    @JsonCreator
    public Channel(@JsonProperty("id") String channelId,
                   @JsonProperty("name") String channelName,
                   @JsonProperty("ts") String channelTs) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelTs = channelTs;
    }
}
