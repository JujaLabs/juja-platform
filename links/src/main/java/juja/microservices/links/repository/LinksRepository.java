package juja.microservices.links.repository;

import juja.microservices.links.model.Link;

import java.util.List;

/**
 * @author Ivan Shapovalov
 */
public interface LinksRepository {
    Link saveLink(String url);

    List<Link> getAllLinks();

    Link getLinkByURL(String url);
}
