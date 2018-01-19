package juja.microservices.links.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

@ApiModel
@Getter
@EqualsAndHashCode
public class SaveLinkRequest {
    @NotEmpty
    @ApiModelProperty(value = "URL of the link to be saved", required = true)
    private String url;
    @NotEmpty
    @ApiModelProperty(value = "Person who saved the link", required = true)
    private String owner;

    @JsonCreator
    public SaveLinkRequest(@JsonProperty("url") String url, @JsonProperty("owner") String owner) {
        this.url = url;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("Link[url='%s', owner='%s']", url, owner);
    }
}
