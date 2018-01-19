package juja.microservices.links.service.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.LinksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 * @author Ben Novikov
 */
@Slf4j
@Service
@AllArgsConstructor
public class LinksServiceImpl implements LinksService {
    private LinksRepository linksRepository;

    @Override
    public Link saveLink(SaveLinkRequest request) {
        Link link;
        String url = request.getUrl();

        link = linksRepository.getLinkByURL(url);
        if (link != null) {
            if (link.isHidden()) {
                link.setHidden(false);
                linksRepository.saveLink(link);
                log.info("Hidden link set visible (not hidden) {}. ", link.toString());
            } else {
                log.info("Link already exists and it's not hidden {}. ", link.toString());
            }
        } else {
            link = linksRepository.saveLink(new Link(url, request.getOwner()));
        }

        return link;
    }

    @Override
    public List<Link> getAllLinks() {
        return linksRepository.getAllLinks();
    }
}
