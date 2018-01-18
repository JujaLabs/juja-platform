package juja.microservices.slack.archive.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
@NoArgsConstructor
public class RawChannel {

    @Id
    private String id;
    private Map<String, Object> channel;

    public RawChannel(Map<String, Object> channel) {
        this.channel = channel;
    }
}
