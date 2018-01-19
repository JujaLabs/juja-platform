package juja.microservices.links.service;

import juja.microservices.links.exception.LinkAlreadyExistsException;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;

import java.util.List;

/**
 * @author Ivan Shapovalov
 */
public interface LinksService {

    Link saveLink(SaveLinkRequest request) throws LinkAlreadyExistsException;

    List<Link> getAllLinks();
}
