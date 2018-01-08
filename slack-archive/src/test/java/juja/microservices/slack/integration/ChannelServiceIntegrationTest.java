package juja.microservices.slack.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.slack.archive.model.dto.ChannelDTO;
import juja.microservices.slack.archive.repository.impl.ChannelRepositoryImpl;
import juja.microservices.slack.archive.service.impl.ChannelServiceImpl;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ChannelServiceIntegrationTest extends BaseIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    private ChannelRepositoryImpl repository;

    @Inject
    private ChannelServiceImpl service;

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void getChannelsTest() {
        List<ChannelDTO> expected = new ArrayList<>();
        expected.add(new ChannelDTO("CHANIDONE", "flood"));
        expected.add(new ChannelDTO("CHANIDTWO", "feedback"));

        List<ChannelDTO> actual = service.getChannels();

        assertNotNull(actual);
        assertThat(actual, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
    }
}
