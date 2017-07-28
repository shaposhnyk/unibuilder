package com.shaposhnyk

import org.junit.Test
import java.net.URI
import kotlin.test.assertEquals

class UniBuilderTest {

    @Test
    fun simpliestUrlCanBeBuilt() {
        val uri = UniBuilder.of("https://www.shaposhnyk.com")
        assertEquals("https://www.shaposhnyk.com", uri.base)
        assertEquals("", uri.path)
        assertEquals(emptyList(), uri.params)
        assertEquals(URI("https://www.shaposhnyk.com"), uri.build())

        val uri2 = UniBuilder.of("https://www.shaposhnyk.com/")
        assertEquals("https://www.shaposhnyk.com", uri2.base)
        assertEquals("/", uri2.path)
        assertEquals(emptyList(), uri2.params)
        assertEquals(URI("https://www.shaposhnyk.com/"), uri2.build())
    }

    @Test
    fun simpleUrlCanBeBuilt() {
        val uri2 = UniBuilder.of("https://www.shaposhnyk.com:8080/page.html")
        assertEquals("https://www.shaposhnyk.com:8080", uri2.base)
        assertEquals("/page.html", uri2.path)
        assertEquals(emptyList(), uri2.params)
        assertEquals(URI("https://www.shaposhnyk.com:8080/page.html"), uri2.build())

        val uri3 = UniBuilder.of("https://www.shaposhnyk.com/page.html")
        assertEquals("https://www.shaposhnyk.com", uri3.base)
        assertEquals("/page.html", uri3.path)
        assertEquals(emptyList(), uri3.params)
        assertEquals(URI("https://www.shaposhnyk.com/page.html"), uri3.build())

        val uri4 = UniBuilder.of("https://www.shaposhnyk.com/page.html?q1=v1&q2=v2")
        assertEquals("https://www.shaposhnyk.com", uri4.base)
        assertEquals("/page.html", uri4.path)
        assertEquals(listOf(Pair("q1", "v1"), Pair("q2", "v2")), uri4.params)
        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1&q2=v2"), uri4.build())
    }

    @Test
    fun paramsCanBeAdded() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com/page.html");

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1"), builder
                .param("q1", "v1")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1&q2=v2"), builder
                .param("q1", "v1")
                .param("q2", "v2")
                .build())
    }

    @Test
    fun sameParamsCanBeAdded() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com/page.html");

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1&q1=v2&q1=v3"), builder
                .param("q1", "v1")
                .param("q1", "v2")
                .param("q1", "v3")
                .build())

    }

    @Test
    fun valuesAreEscaped() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com/page.html");

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1%3Dv2"), builder
                .param("q1", "v1=v2")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1%3Cv2"), builder
                .param("q1", "v1<v2")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1%3Cv2"), builder
                .param("q1", "v1<v2")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?q1=v1%26v2"), builder
                .param("q1", "v1&v2")
                .build())
    }

    @Test
    fun paramsAreEscaped() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com/page.html");

        assertEquals(URI("https://www.shaposhnyk.com/page.html?qv1%3Dv2=1"), builder
                .param("qv1=v2", "1")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?qv1%3Cv2=1"), builder
                .param("qv1<v2", "1")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?qv1%3Cv2=1"), builder
                .param("qv1<v2", "1")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/page.html?qv1%26v2=1"), builder
                .param("qv1&v2", "1")
                .build())
    }

    @Test
    fun pathChanged() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com/page.html");

        assertEquals(URI("https://www.shaposhnyk.com/about.html"), builder
                .path("/about.html")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com/about.html?q1=v1"), builder
                .path("/about.html")
                .param("q1", "v1")
                .build())

        assertEquals(URI("https://www.shaposhnyk.com?q1=v1"), builder
                .path("")
                .param("q1", "v1")
                .build())
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidPathRejected() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com/page.html");

        assertEquals(URI("https://www.shaposhnyk.com/about.html"), builder
                .path("about.html")
                .build())
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidAppendedPathRejected() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com");

        assertEquals(URI("https://www.shaposhnyk.com/about.html"), builder
                .appendPath("about.html")
                .build())
    }

    @Test
    fun paramsCanBeDropped() {
        val builder = UniBuilder.of("https://www.shaposhnyk.com?q=v1");

        assertEquals(URI("https://www.shaposhnyk.com"), builder
                .dropParams()
                .build())
    }
}
