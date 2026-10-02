package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_stripsControlCharsFromUrls {

    @Test
    void stripsControlCharsFromUrls() {
        // Base URL contains a leading newline (\n) and an embedded tab (\t);
        // relative URL contains carriage return (\r), newline (\n), tab (\t), and backspace (\b).
        // resolve() strips all control characters before resolving, so the relative URL
        // "foo:bar" is treated as an absolute URL and returned directly.
        String baseUrlWithControlChars = "\nhttps://\texample.com/";
        String relUrlWithControlChars  = "\r\nfo\to:ba\br";
        String expectedUrl = "foo:bar";

        assertEquals(expectedUrl, resolve(baseUrlWithControlChars, relUrlWithControlChars));
    }
}
