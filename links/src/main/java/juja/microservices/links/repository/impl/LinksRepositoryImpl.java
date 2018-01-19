package juja.microservices.links.repository.impl;

import juja.microservices.links.model.Link;
import juja.microservices.links.repository.LinksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */
@Slf4j
@Repository
public class LinksRepositoryImpl implements LinksRepository {
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.collection}")
    private String mongoCollectionName;

    public LinksRepositoryImpl(MongoTemplate mongoTemplate, @Value("${spring.data.mongodb.collection}") String mongoCollectionName) {
        this.mongoTemplate = mongoTemplate;
        this.mongoCollectionName = mongoCollectionName;
    }

    @Override
    public Link saveLink(Link link) {
        mongoTemplate.save(link, mongoCollectionName);
        log.info("Successfully saved link {}.", link.toString());

        return link;
    }

    @Override
    public Link getLinkByURL(String url) {
        Link link = mongoTemplate.findOne(query(where("url").is(url)),
                Link.class, mongoCollectionName);

        return link;
    }

    @Override
    public Link getLinkByURLAndOwner(String url, String owner) {
        Link link = mongoTemplate.findOne(query(where("url").is(url).and("owner").is(owner)),
                Link.class, mongoCollectionName);

        return link;
    }

    @Override
    public List<Link> getAllLinks() {
        return mongoTemplate.findAll(Link.class, mongoCollectionName);
    }
}
