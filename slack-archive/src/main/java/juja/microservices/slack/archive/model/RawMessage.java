package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Map;

@Getter
@ToString
public class RawMessage {

    @Id
    @Field("_id")
    private String id;
    private Map<String, Object> rawMessage;
    private String channel;
    private Date date;

    public RawMessage(@JsonProperty("_id") String id,
                      @JsonProperty("rawMessage") Map<String, Object> rawMessage,
                      @JsonProperty("channel") String channel,
                      @JsonProperty("date") Date date) {
        this.id = id;
        this.rawMessage = rawMessage;
        this.channel = channel;
        this.date = date;
    }
}
