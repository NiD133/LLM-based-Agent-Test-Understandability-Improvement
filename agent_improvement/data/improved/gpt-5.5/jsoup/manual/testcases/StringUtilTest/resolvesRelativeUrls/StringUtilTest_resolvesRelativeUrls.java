package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_resolvesRelativeUrls {

    @Test
    public void resolvesRelativeUrls() {
        assertResolves("http://example.com/one/two?three", "http://example.com", "./one/two?three");
        assertResolves("http://example.com/one/two?three", "http://example.com?one", "./one/two?three");
        assertResolves("http://example.com/one/two?three#four", "http://example.com", "./one/two?three#four");
        assertResolves("https://example.com/one", "http://example.com/", "https://example.com/one");
        assertResolves("http://example.com/one/two.html", "http://example.com/two/", "../one/two.html");
        assertResolves("https://example2.com/one", "https://example.com/", "//example2.com/one");
        assertResolves("https://example.com:8080/one", "https://example.com:8080", "./one");
        assertResolves("https://example2.com/one", "http://example.com/", "https://example2.com/one");
        assertResolves("https://example.com/one", "wrong", "https://example.com/one");
        assertResolves("https://example.com/one", "https://example.com/one", "");
        assertResolves("", "wrong", "also wrong");
        assertResolves("ftp://example.com/one", "ftp://example.com/two/", "../one");
        assertResolves("ftp://example.com/one/two.c", "ftp://example.com/one/", "./two.c");
        assertResolves("ftp://example.com/one/two.c", "ftp://example.com/one/", "two.c");

        assertResolvesRfc3986AbnormalExample("http://example.com/g", "../../../g");
        assertResolvesRfc3986AbnormalExample("http://example.com/g", "../../../../g");
        assertResolvesRfc3986AbnormalExample("http://example.com/g", "/./g");
        assertResolvesRfc3986AbnormalExample("http://example.com/g", "/../g");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g.", "g.");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/.g", ".g");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g..", "g..");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/..g", "..g");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/g", "./../g");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g/", "./g/.");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g/h", "g/./h");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/h", "g/../h");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g;x=1/y", "g;x=1/./y");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/y", "g;x=1/../y");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g?y/./x", "g?y/./x");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g?y/../x", "g?y/../x");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g#s/./x", "g#s/./x");
        assertResolvesRfc3986AbnormalExample("http://example.com/b/c/g#s/../x", "g#s/../x");
    }

    private static void assertResolvesRfc3986AbnormalExample(String expectedUrl, String relativeUrl) {
        assertResolves(expectedUrl, "http://example.com/b/c/d;p?q", relativeUrl);
    }

    private static void assertResolves(String expectedUrl, String baseUrl, String relativeUrl) {
        assertEquals(expectedUrl, resolve(baseUrl, relativeUrl));
    }
}
