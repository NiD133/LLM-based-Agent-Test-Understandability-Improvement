package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_allowsSpaceInUrl {
    private static final String BASE_URL = "HTTPS://example.com/example/";
    private static final String RELATIVE_URL_WITH_SPACE = "../foo bar/";
    private static final String EXPECTED_RESOLVED_URL = "https://example.com/foo bar/";

    @Test
    void allowsSpaceInUrl() {
        String resolvedUrl = resolve(BASE_URL, RELATIVE_URL_WITH_SPACE);

        assertEquals(EXPECTED_RESOLVED_URL, resolvedUrl);
    }
}
