package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_resolvesRelativeUrls {

    /**
     * Asserts that resolving {@code relUrl} against {@code baseUrl} produces {@code expected}.
     * Reads naturally as: base + rel -> expected.
     */
    private static void assertResolves(String expected, String baseUrl, String relUrl) {
        assertEquals(expected, resolve(baseUrl, relUrl));
    }

    @Test
    public void resolvesRelativeUrls() {
        // current-directory ("./") and query / fragment handling
        assertResolves("http://example.com/one/two?three", "http://example.com", "./one/two?three");
        assertResolves("http://example.com/one/two?three", "http://example.com?one", "./one/two?three");
        assertResolves("http://example.com/one/two?three#four", "http://example.com", "./one/two?three#four");

        // a relative URL that is itself absolute replaces the base
        assertResolves("https://example.com/one", "http://example.com/", "https://example.com/one");
        assertResolves("https://example2.com/one", "http://example.com/", "https://example2.com/one");

        // parent-directory ("../") navigation
        assertResolves("http://example.com/one/two.html", "http://example.com/two/", "../one/two.html");

        // protocol-relative ("//host") and explicit port
        assertResolves("https://example2.com/one", "https://example.com/", "//example2.com/one");
        assertResolves("https://example.com:8080/one", "https://example.com:8080", "./one");

        // an unusable base falls back to the relative URL when it is absolute on its own
        assertResolves("https://example.com/one", "wrong", "https://example.com/one");
        // both base and relative are unusable -> empty result
        assertResolves("", "wrong", "also wrong");

        // empty relative URL yields the base unchanged
        assertResolves("https://example.com/one", "https://example.com/one", "");

        // non-http schemes (ftp) resolve the same way
        assertResolves("ftp://example.com/one", "ftp://example.com/two/", "../one");
        assertResolves("ftp://example.com/one/two.c", "ftp://example.com/one/", "./two.c");
        assertResolves("ftp://example.com/one/two.c", "ftp://example.com/one/", "two.c");

        // examples taken from rfc3986 section 5.4.2
        String rfcBase = "http://example.com/b/c/d;p?q";
        // excess "../" segments are clamped at the root
        assertResolves("http://example.com/g", rfcBase, "../../../g");
        assertResolves("http://example.com/g", rfcBase, "../../../../g");
        // dot segments within an absolute path are removed
        assertResolves("http://example.com/g", rfcBase, "/./g");
        assertResolves("http://example.com/g", rfcBase, "/../g");
        // dots that are part of a file name are preserved
        assertResolves("http://example.com/b/c/g.", rfcBase, "g.");
        assertResolves("http://example.com/b/c/.g", rfcBase, ".g");
        assertResolves("http://example.com/b/c/g..", rfcBase, "g..");
        assertResolves("http://example.com/b/c/..g", rfcBase, "..g");
        // mixed dot segments and path elements
        assertResolves("http://example.com/b/g", rfcBase, "./../g");
        assertResolves("http://example.com/b/c/g/", rfcBase, "./g/.");
        assertResolves("http://example.com/b/c/g/h", rfcBase, "g/./h");
        assertResolves("http://example.com/b/c/h", rfcBase, "g/../h");
        // path parameters (";x=1") around dot segments
        assertResolves("http://example.com/b/c/g;x=1/y", rfcBase, "g;x=1/./y");
        assertResolves("http://example.com/b/c/y", rfcBase, "g;x=1/../y");
        // dot segments inside the query string are left untouched
        assertResolves("http://example.com/b/c/g?y/./x", rfcBase, "g?y/./x");
        assertResolves("http://example.com/b/c/g?y/../x", rfcBase, "g?y/../x");
        // dot segments inside the fragment are left untouched
        assertResolves("http://example.com/b/c/g#s/./x", rfcBase, "g#s/./x");
        assertResolves("http://example.com/b/c/g#s/../x", rfcBase, "g#s/../x");
    }
}
