package juja.microservices.links.repository;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import juja.microservices.links.model.Link;
import juja.microservices.links.repository.impl.LinksRepositoryImpl;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.assertThat;


/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 */

public class LinksRepositoryTest {

    private static final String DB_NAME = "test-links";
    private static final String COLLECTION_NAME ="links";
    private MongoTemplate mongoTemplate;
    private LinksRepository linksRepository;
    private List<Link> expectedList;
    @ClassRule
    public static InMemoryMongoDb mongod = newInMemoryMongoDbRule().build();
    @Rule
    public MongoDbRule mongoRule = newMongoDbRule().defaultEmbeddedMongoDb(DB_NAME);

    @Before
    public void setUp() {
        expectedList = Arrays.asList(
                new Link("5a60b8905662c03d58d85649", "www.test1.com", "1", false),
                new Link("5a60b8905662c03d58d85644","www.test2.net", "2", true));
        mongoTemplate = new MongoTemplate(mongoRule.getDatabaseOperation().connectionManager(), DB_NAME);
        mongoTemplate.dropCollection(COLLECTION_NAME);
        linksRepository = new LinksRepositoryImpl(mongoTemplate, COLLECTION_NAME);
    }

    @Test
    public void testGetAllLinks() {
        mongoTemplate.insert(expectedList, COLLECTION_NAME);
        List<Link> result = linksRepository.getAllLinks();
        assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void testSaveLink() {
        expectedList.forEach(linksRepository::saveLink);
        List<Link> result = mongoTemplate.findAll(Link.class, COLLECTION_NAME);
        assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedList.toArray()));
    }
}
