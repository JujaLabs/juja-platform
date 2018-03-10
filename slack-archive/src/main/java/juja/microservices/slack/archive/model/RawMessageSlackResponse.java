package juja.microservices.slack.archive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class RawMessageSlackResponse {
    @JsonProperty("ok")
    private Boolean ok;
    @JsonProperty("messages")
    private List<Map<String, Object>> messages;
    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("error")
    private String error;
}


