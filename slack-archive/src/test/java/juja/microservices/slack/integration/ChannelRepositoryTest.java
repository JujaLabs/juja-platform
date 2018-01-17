package juja.microservices.slack.integration;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.repository.impl.ChannelRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ChannelRepositoryTest extends BaseIntegrationTest {

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
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void saveChannelsTest() {

        final String theChannelId = "newChannelId" ;
        final String theChannelName = "newChannelName";

        Channel channelForSave = new Channel(theChannelId, theChannelName);
        List<Channel> channels = new ArrayList<>();
        channels.add(channelForSave);

        repository.saveRawChannels(channels);

        List<Channel> actualChannelList = repository.getChannels();

        assertEquals(findChannels(actualChannelList, theChannelId, theChannelName).size(), 1);
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void saveChannelsToEmptyCollection() {

        final String theChannelId1 = "newChannelId1" ;
        final String theChannelName1 = "newChannelName1";

        final String theChannelId2 = "newChannelId2";
        final String theChannelName2 = "newChannelName2";

        Channel channelForSave1 = new Channel(theChannelId1, theChannelName1);
        Channel channelForSave2 = new Channel(theChannelId2, theChannelName2);
        List<Channel> channels = new ArrayList<>();
        channels.add(channelForSave1);
        channels.add(channelForSave2);

        repository.saveRawChannels(channels);

        List<Channel> actualChannelList = repository.getChannels();

        assertEquals(findChannels(actualChannelList, theChannelId1, theChannelName1).size(), 1);
        assertEquals(findChannels(actualChannelList, theChannelId2, theChannelName2).size(), 1);
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void updateChannelName() {

        final String theChannelId1 = "CHANIDONE" ;
        final String theChannelName1 = "newChannelName1";

        Channel channelForSave1 = new Channel(theChannelId1, theChannelName1);

        List<Channel> channels = new ArrayList<>();
        channels.add(channelForSave1);

        repository.saveRawChannels(channels);

        List<Channel> actualChannelList = repository.getChannels();

        assertEquals(findChannels(actualChannelList, theChannelId1, theChannelName1).size(), 1);
    }

    @Test
    @UsingDataSet(locations = "/datasets/channels.json")
    public void ifAddExistChannel() {

        final String theChannelId1 = "CHANIDONE" ;
        final String theChannelName1 = "flood";

        Channel channelForSave1 = new Channel(theChannelId1, theChannelName1);

        List<Channel> channels = new ArrayList<>();
        channels.add(channelForSave1);

        repository.saveRawChannels(channels);

        List<Channel> actualChannelList = repository.getChannels();

        assertEquals(actualChannelList.size(), 2);
    }

    private List<Channel> findChannels(List<Channel> channels, String channelId, String channelName){
        return channels.stream()
                .filter(channel -> channel.getChannelId().equals(channelId))
                .filter(channel -> channel.getChannelName().equals(channelName))
                .collect(Collectors.toList());
    }
}
