package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.entity.Channel;
import juja.microservices.slack.archive.model.dto.ChannelDTO;
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
    ArgumentCaptor<List<Channel>> channelsCaptor;

    @Before
    public void setUp() {
        service = new ChannelServiceImpl(repository);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(repository);
    }

    @Test
    public void saveChannels() {

        List<ChannelDTO> channelsDTO = new ArrayList<>();
        channelsDTO.add(new ChannelDTO("CHANONEID", "flood"));
        channelsDTO.add(new ChannelDTO("CHANTWOID", "feedback"));


        List<Channel> expected = new ArrayList<>();
        expected.add(new Channel("CHANONEID", "flood"));
        expected.add(new Channel("CHANTWOID", "feedback"));

        service.saveChannels(channelsDTO);

        verify(repository).saveOrUpdateChannels(channelsCaptor.capture());

        assertEquals(expected, channelsCaptor.getValue());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getChannelsTest() {
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel("CHANONEID", "flood"));
        channels.add(new Channel("CHANTWOID", "feedback"));

        List<ChannelDTO> expected = new ArrayList<>();
        expected.add(new ChannelDTO("CHANONEID", "flood"));
        expected.add(new ChannelDTO("CHANTWOID", "feedback"));

        when(repository.getChannels()).thenReturn(channels);

        List<ChannelDTO> actual = service.getChannels();

        assertEquals(expected, actual);
        verify(repository).getChannels();
        verifyNoMoreInteractions(repository);
    }
}
