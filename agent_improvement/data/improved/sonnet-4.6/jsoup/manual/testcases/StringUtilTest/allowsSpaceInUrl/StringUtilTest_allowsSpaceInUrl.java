package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_allowsSpaceInUrl {

    @Test
    void allowsSpaceInUrl() {
        // resolve() preserves literal spaces in path segments and normalises the protocol to lowercase
        String baseUrl     = "HTTPS://example.com/example/";
        String relativeUrl = "../foo bar/";
        String expectedUrl = "https://example.com/foo bar/";

        assertEquals(expectedUrl, resolve(baseUrl, relativeUrl));
    }
}
