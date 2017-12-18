package juja.microservices.links.service.impl;

import juja.microservices.links.exception.InternalErrorException;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class LinksServiceImpl implements LinksService {
    private LinksRepository linksRepository;

    public LinksServiceImpl(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public Link saveLink(SaveLinkRequest request) throws Exception {
        Link link = linksRepository.saveLink(request);

        if (link == null) {
            throw new InternalErrorException(String.format("Failed to process '%s'", request.toString()));
        }

        return link;
    }

    @Override
    public Set<Link> getAllLinks() {
        return null;
    }
}
