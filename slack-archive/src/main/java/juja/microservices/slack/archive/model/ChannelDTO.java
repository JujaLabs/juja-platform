package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChannelDTO {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String ts;

    public ChannelDTO(String id, String name, String ts) {
        this.id = id;
        this.name = name;
        this.ts = ts;
    }
}
