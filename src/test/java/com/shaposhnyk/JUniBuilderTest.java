package com.shaposhnyk;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

/**
 * Created by vlad on 15.08.17.
 */
public class JUniBuilderTest {

    @Test
    public void simpliestUrlCanBeBuilt() throws URISyntaxException {
        final UniBuilder uri = UniBuilder.Factory.of("https://www.shaposhnyk.com");
        assertEquals("https://www.shaposhnyk.com", uri.getBase());
        assertEquals("", uri.getPath());
        assertEquals(emptyList(), uri.getParams());
        assertEquals(new URI("https://www.shaposhnyk.com"), uri.build());

        final UniBuilder uri2 = UniBuilder.Factory.of("https://www.shaposhnyk.com/");
        assertEquals("https://www.shaposhnyk.com", uri2.getBase());
        assertEquals("/", uri2.getPath());
        assertEquals(emptyList(), uri2.getParams());
        assertEquals(new URI("https://www.shaposhnyk.com/"), uri2.build());
    }

    @Test
    public void simpleWsUrl() throws URISyntaxException {
        URI uri = UniBuilder.Factory.of("https://www.shaposhnyk.com/svc")
                .appendPath("/clusters?expand=applicationBlock")
                .param("itEnv", "DEV")
                .param("expand", "hosts")
                .build();

        assertEquals(uri.toString(), "https://www.shaposhnyk.com/svc/clusters?expand=applicationBlock&itEnv=DEV&expand=hosts");
    }
}
