package juja.microservices.slack.archive.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.slack.archive.repository.impl.ArchiveRepositoryImpl;
import juja.microservices.slack.archive.service.impl.MessageServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ArchiveServiceIntegrationTest extends BaseIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    private ArchiveRepositoryImpl repository;

    @Inject
    private MessageServiceImpl service;

    @Test
    public void testMockCreation() {
        assertNotNull(service);
    }


    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void getChannelsTest() {
//        List<ChannelDTO> expected = new ArrayList<>();
//        expected.add(new ChannelDTO("CHANIDONE", "flood"));
//        expected.add(new ChannelDTO("CHANIDTWO", "feedback"));
//
//       // List<ChannelDTO> actual = service.getChannels();
//
//        assertNotNull(actual);
//        assertThat(actual, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
    }
}
