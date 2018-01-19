package juja.microservices.links.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @ApiModelProperty(value = "Unique ID of the link", required = true)
    private String id;
    @NotEmpty
    @ApiModelProperty(value = "URL of the link. No duplicates for owner+URL pairs", required = true)
    private String url;
    @NotEmpty
    @ApiModelProperty(value = "Owner of the link", required = true)
    private String owner;
    @ApiModelProperty(value = "Flag that means the link is hidden or not", required = false, allowEmptyValue = true)
    private boolean hidden;

    //used to save to database, _id is auto generated, _hidden is false by default
    public Link(String url, String owner) {
        this.url = url;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("Link[id='%s', url='%s', owner='%s', hidden='%s']", id, url, owner, hidden);
    }
}
