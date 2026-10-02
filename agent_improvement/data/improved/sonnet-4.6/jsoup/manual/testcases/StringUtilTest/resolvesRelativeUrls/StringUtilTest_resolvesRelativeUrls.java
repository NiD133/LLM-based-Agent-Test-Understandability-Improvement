package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_resolvesRelativeUrls {

    // Base URL used by all RFC 3986 Section 5.4.2 abnormal-example tests
    private static final String RFC3986_BASE = "http://example.com/b/c/d;p?q";

    @Test
    public void resolvesBasicRelativePath() {
        assertEquals("http://example.com/one/two?three",
            resolve("http://example.com", "./one/two?three"));
    }

    @Test
    public void resolvesRelativePathFromBaseWithQueryString() {
        // The query string on the base URL is dropped when the relative reference starts with "./"
        assertEquals("http://example.com/one/two?three",
            resolve("http://example.com?one", "./one/two?three"));
    }

    @Test
    public void resolvesRelativePathWithFragment() {
        assertEquals("http://example.com/one/two?three#four",
            resolve("http://example.com", "./one/two?three#four"));
    }

    @Test
    public void absoluteRelativeUrlOverridesBaseSchemeAndHost() {
        // An absolute URL in the relative position replaces scheme, host, and path
        assertEquals("https://example.com/one",
            resolve("http://example.com/", "https://example.com/one"));
        assertEquals("https://example2.com/one",
            resolve("http://example.com/", "https://example2.com/one"));
    }

    @Test
    public void resolvesParentDirectoryTraversal() {
        assertEquals("http://example.com/one/two.html",
            resolve("http://example.com/two/", "../one/two.html"));
    }

    @Test
    public void resolvesProtocolRelativeUrl() {
        // Protocol-relative URLs (//) inherit the scheme from the base
        assertEquals("https://example2.com/one",
            resolve("https://example.com/", "//example2.com/one"));
    }

    @Test
    public void resolvesUrlWithPortInBase() {
        assertEquals("https://example.com:8080/one",
            resolve("https://example.com:8080", "./one"));
    }

    @Test
    public void resolvesAbsoluteRelativeUrlWhenBaseIsInvalid() {
        // If the base is malformed but the relative URL is itself absolute, return the relative URL as-is
        assertEquals("https://example.com/one",
            resolve("wrong", "https://example.com/one"));
    }

    @Test
    public void emptyRelativeUrlReturnsBase() {
        assertEquals("https://example.com/one",
            resolve("https://example.com/one", ""));
    }

    @Test
    public void bothInvalidUrlsReturnEmptyString() {
        assertEquals("", resolve("wrong", "also wrong"));
    }

    @Test
    public void resolvesFtpUrls() {
        assertEquals("ftp://example.com/one",
            resolve("ftp://example.com/two/", "../one"));
        assertEquals("ftp://example.com/one/two.c",
            resolve("ftp://example.com/one/", "./two.c"));
        assertEquals("ftp://example.com/one/two.c",
            resolve("ftp://example.com/one/", "two.c"));
    }

    /**
     * Abnormal examples from RFC 3986 Section 5.4.2.
     * Verifies correct handling of excess parent references and unusual dot segments.
     */
    @Test
    public void resolvesRfc3986AbnormalExamples() {
        // Excess ".." segments beyond the root collapse to the root
        assertEquals("http://example.com/g", resolve(RFC3986_BASE, "../../../g"));
        assertEquals("http://example.com/g", resolve(RFC3986_BASE, "../../../../g"));

        // Dot segments that begin an absolute path are removed
        assertEquals("http://example.com/g", resolve(RFC3986_BASE, "/./g"));
        assertEquals("http://example.com/g", resolve(RFC3986_BASE, "/../g"));

        // Trailing/leading dots that are part of a segment name (not separators) are kept
        assertEquals("http://example.com/b/c/g.",  resolve(RFC3986_BASE, "g."));
        assertEquals("http://example.com/b/c/.g",  resolve(RFC3986_BASE, ".g"));
        assertEquals("http://example.com/b/c/g..", resolve(RFC3986_BASE, "g.."));
        assertEquals("http://example.com/b/c/..g", resolve(RFC3986_BASE, "..g"));

        // Dot segment combinations within a path
        assertEquals("http://example.com/b/g",   resolve(RFC3986_BASE, "./../g"));
        assertEquals("http://example.com/b/c/g/", resolve(RFC3986_BASE, "./g/."));
        assertEquals("http://example.com/b/c/g/h", resolve(RFC3986_BASE, "g/./h"));
        assertEquals("http://example.com/b/c/h",   resolve(RFC3986_BASE, "g/../h"));

        // Dot-like segments inside path parameters are not treated as traversal
        assertEquals("http://example.com/b/c/g;x=1/y", resolve(RFC3986_BASE, "g;x=1/./y"));
        assertEquals("http://example.com/b/c/y",        resolve(RFC3986_BASE, "g;x=1/../y"));

        // Slashes inside query strings and fragments are not treated as path traversal
        assertEquals("http://example.com/b/c/g?y/./x",  resolve(RFC3986_BASE, "g?y/./x"));
        assertEquals("http://example.com/b/c/g?y/../x", resolve(RFC3986_BASE, "g?y/../x"));
        assertEquals("http://example.com/b/c/g#s/./x",  resolve(RFC3986_BASE, "g#s/./x"));
        assertEquals("http://example.com/b/c/g#s/../x", resolve(RFC3986_BASE, "g#s/../x"));
    }
}
