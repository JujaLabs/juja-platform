package juja.microservices.slack.archive.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.repository.impl.ChannelRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ChannelRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private ChannelRepositoryImpl repository;

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void getChannelsTest() {
        List<Channel> channels = repository.getChannels();
        Channel channel = channels.get(0);

        assertNotNull(channels);
        assertEquals(2, channels.size());
        assertEquals("CHANIDONE", channel.getChannelId());
        assertEquals("flood", channel.getChannelName());
        assertEquals("9876543219.000321", channel.getChannelTs());
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void saveChannelTest() {

        final String theChannelId = "newChannelId";
        final String theChannelName = "newChannelName";
        final String theChannelTs = "9999999999.000321";

        Channel channelForSave = new Channel(theChannelId, theChannelName, theChannelTs);

        repository.saveChannel(channelForSave);

        List<Channel> actualChannelList = repository.getChannels();

        assertEquals(1, findChannels(actualChannelList, theChannelId, theChannelName, theChannelTs).size());
        assertEquals(3, actualChannelList.size());
    }


    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void updateChannelTs() {

        final String theChannelId = "CHANIDONE";
        final String theChannelName = "flood";
        final String theChannelTs = "1111111111.000111";

        Channel channelForSave = new Channel(theChannelId, theChannelName, theChannelTs);

        repository.saveChannel(channelForSave);

        List<Channel> actualChannelList = repository.getChannels();
        assertEquals(0, findChannels(actualChannelList, theChannelId, theChannelName, "9876543219.000321").size());
        assertEquals(1, findChannels(actualChannelList, theChannelId, theChannelName, "1111111111.000111").size());
        assertEquals(2, actualChannelList.size());
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void updateChannelName() {

        final String theChannelId = "CHANIDONE";
        final String theChannelName = "newName";
        final String theChannelTs = "9876543219.000321";

        Channel channelForSave = new Channel(theChannelId, theChannelName, theChannelTs);

        repository.saveChannel(channelForSave);

        List<Channel> actualChannelList = repository.getChannels();
        assertEquals(0, findChannels(actualChannelList, theChannelId, "flood", theChannelTs).size());
        assertEquals(1, findChannels(actualChannelList, theChannelId, theChannelName, theChannelTs).size());
        assertEquals(2, actualChannelList.size());
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void ifAddExistChannel() {

        final String theChannelId = "CHANIDONE";
        final String theChannelName = "flood";
        final String theChannelTs = "1234567890.000123";

        Channel channelForSave1 = new Channel(theChannelId, theChannelName, theChannelTs);

        repository.saveChannel(channelForSave1);

        List<Channel> actualChannelList = repository.getChannels();

        assertEquals(1, findChannels(actualChannelList, theChannelId, theChannelName, theChannelTs).size());
        assertEquals(2, actualChannelList.size());
    }

    private List<Channel> findChannels(List<Channel> channels, String channelId, String channelName, String channelTs) {
        return channels.stream()
                .filter(channel -> channel.getChannelId().equals(channelId))
                .filter(channel -> channel.getChannelName().equals(channelName))
                .filter(channel -> channel.getChannelTs().equals(channelTs))
                .collect(Collectors.toList());
    }
}
