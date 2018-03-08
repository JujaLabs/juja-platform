package juja.microservices.slack.archive.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document
public class RawMessage {

    @Id
    @Field("_id")
    private String id;
    private Map<String, Object> rawMessage;
    private String channel;
    private Date date;

    public RawMessage(String id, Map<String, Object> rawMessage, String channel, Date date) {
        this.id = id;
        this.rawMessage = rawMessage;
        this.channel = channel;
        this.date = date;
    }
}
