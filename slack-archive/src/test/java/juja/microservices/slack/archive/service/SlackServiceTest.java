package juja.microservices.slack.archive.service;

import juja.microservices.slack.archive.model.RawMessage;
import juja.microservices.slack.archive.repository.SlackRepository;
import juja.microservices.slack.archive.service.impl.SlackServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlackServiceTest {

    private SlackService service;

    @Rule
    final public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private SlackRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new SlackServiceImpl(repository);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(repository);
    }

    @Test
    public void getRawMessageTest() {
        Map<String, Object> rawMessageMap = new LinkedHashMap<>();
        rawMessageMap.put("type", "message");
        rawMessageMap.put("user", "U8ET8UJ0L");
        rawMessageMap.put("text", "first message");
        rawMessageMap.put("ts", "1517498771.000708");
        RawMessage rawMessage1 = new RawMessage("1517498771.000708", rawMessageMap,
                "C8FU4T48P", new Date(Long.parseLong("1517498771708")));

        Map<String, Object> rawMessageMap2 = new LinkedHashMap<>();
        rawMessageMap2.put("type", "message");
        rawMessageMap2.put("user", "U8ET8QQQQ");
        rawMessageMap2.put("text", "last message");
        rawMessageMap2.put("ts", "1520599268.000174");
        RawMessage rawMessage2 = new RawMessage("1520599268.000174", rawMessageMap2,
                "C8FU4T48P", new Date(Long.parseLong("1520599268174")));

        List<RawMessage> messages = Arrays.asList(rawMessage1, rawMessage2);

        Map<String, Object> rawMessageMapExpected = new LinkedHashMap<>();
        rawMessageMapExpected.put("type", "message");
        rawMessageMapExpected.put("user", "U8ET8UJ0L");
        rawMessageMapExpected.put("text", "first message");
        rawMessageMapExpected.put("ts", "1517498771.000708");
        RawMessage rawMessageExpected1 = new RawMessage("1517498771.000708", rawMessageMapExpected,
                "C8FU4T48P", new Date(Long.parseLong("1517498771708")));

        Map<String, Object> rawMessageMapExpected2 = new LinkedHashMap<>();
        rawMessageMapExpected2.put("type", "message");
        rawMessageMapExpected2.put("user", "U8ET8QQQQ");
        rawMessageMapExpected2.put("text", "last message");
        rawMessageMapExpected2.put("ts", "1520599268.000174");
        RawMessage rawMessageExpected2 = new RawMessage("1520599268.000174", rawMessageMapExpected2,
                "C8FU4T48P", new Date(Long.parseLong("1520599268174")));

        List<RawMessage> messagesExpected = Arrays.asList(rawMessageExpected1, rawMessageExpected2);

        when(repository.getRawMessages("C8FU4T48P", "1513275104.000059")).thenReturn(messages);

        List<RawMessage> actual = service.getRawMessage("C8FU4T48P", "1513275104.000059");

        assertEquals(messagesExpected, actual);
        verify(repository).getRawMessages("C8FU4T48P", "1513275104.000059");
        verifyNoMoreInteractions(repository);
    }
}
