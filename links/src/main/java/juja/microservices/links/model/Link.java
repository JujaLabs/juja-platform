package juja.microservices.links.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Ivan Shapovalov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private String id;
    @NotEmpty
    private String url;

    private boolean archived;

    public Link(String url) {
        this.url = url;
    }
}