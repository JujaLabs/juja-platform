package juja.microservices.slack.archive.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Channel {
    @Id
    private String id;
    private String channelId;
    private String channelName;

    @JsonCreator
    public Channel(@JsonProperty("id") String channelId, @JsonProperty("name") String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }




}
