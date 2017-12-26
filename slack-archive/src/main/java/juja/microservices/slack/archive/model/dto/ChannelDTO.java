package juja.microservices.slack.archive.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
@Data
@AllArgsConstructor
public class ChannelDTO {
    @JsonProperty("id")
    private String channelId;
    @JsonProperty("name")
    private String name;
}
