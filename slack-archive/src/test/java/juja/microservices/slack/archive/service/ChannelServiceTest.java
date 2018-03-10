package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.Channel;
import juja.microservices.slack.archive.repository.ChannelRepository;
import juja.microservices.slack.archive.service.impl.ChannelServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChannelServiceTest {

    private ChannelService service;

    @Rule
    final public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ChannelRepository repository;

    @Captor
    private ArgumentCaptor<Channel> channelsCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ChannelServiceImpl(repository);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(repository);
    }

    @Test
    public void getChannelsTest(){
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel("CHANONEID", "flood", "9876543219.000321"));
        channels.add(new Channel("CHANTWOID", "feedback", "1234567891.000123"));

        List<Channel> expected = new ArrayList<>();
        expected.add(new Channel("CHANONEID", "flood", "9876543219.000321"));
        expected.add(new Channel("CHANTWOID", "feedback", "1234567891.000123"));

        when(repository.getChannels()).thenReturn(channels);

        List<Channel> actual = service.getChannels();

        assertEquals(expected, actual);
        verify(repository).getChannels();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void saveChannelTest(){
        Channel channel = new Channel("CHANONEID", "flood", "9876543219.000321");

        Channel expected = new Channel("CHANONEID", "flood", "9876543219.000321");

        service.saveChannel(channel);

        verify(repository).saveChannel(channelsCaptor.capture());

        assertEquals(expected, channelsCaptor.getValue());
        verifyNoMoreInteractions(repository);
    }
}
