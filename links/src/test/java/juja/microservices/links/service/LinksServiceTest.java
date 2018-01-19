package juja.microservices.links.service;

import juja.microservices.links.exception.LinkAlreadyExistsException;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.repository.LinksRepository;
import juja.microservices.links.service.impl.LinksServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Shapovalov
 * @author Vladimir Zadorozhniy
 * @author Ben Novikov
 */

@RunWith(MockitoJUnitRunner.class)
public class LinksServiceTest {
    @Rule
    final public ExpectedException expectedException = ExpectedException.none();
    private LinksService linksService;
    @Mock
    private LinksRepository linksRepository;

    private final String url = "http://test.com";
    private final String id = "5a30508811d3b338a0b3f85c";
    private final String owner = "a-user";
    private final Link expected = new Link(id, url, owner, false);
    private final SaveLinkRequest request = new SaveLinkRequest(url, owner);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        linksService = new LinksServiceImpl(linksRepository);
    }

    @Test
    public void saveNewLinkTest() {
        Link saveLink = new Link(url, owner);

        when(linksRepository.getLinkByURL(url)).thenReturn(null);
        when(linksRepository.saveLink(saveLink)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(url);
        verify(linksRepository).saveLink(saveLink);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void saveWhenExistsHiddenLinkTest() {
        Link saveLink = new Link(id, url, owner, false);
        expected.setHidden(true);

        when(linksRepository.getLinkByURL(url)).thenReturn(expected);
        when(linksRepository.saveLink(saveLink)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(url);
        verify(linksRepository).saveLink(saveLink);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void saveWhenExistsNotHiddenLinkTest() {
        expectedException.expect(LinkAlreadyExistsException.class);
        expected.setHidden(false);

        when(linksRepository.getLinkByURL(url)).thenReturn(expected);

        Link actual = linksService.saveLink(request);

        assertEquals(expected, actual);
        verify(linksRepository).getLinkByURL(url);
        verifyNoMoreInteractions(linksRepository);
    }

    @Test
    public void testGetAllLinks() {
        List<Link> expectedList = Arrays.asList(
                new Link("www.test1.com", "1"),
                new Link("www.test2.net", "2"));
        when(linksRepository.getAllLinks()).thenReturn(expectedList);
        List<Link> result = linksService.getAllLinks();
        assertEquals(result, expectedList);
    }
}
