
package juja.microservices.slack.archive.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class ChannelSlackResponse {

    @JsonProperty("ok")
    private Boolean ok;
    @JsonProperty("channels")
    private List<ChannelDTO> channels;
}
