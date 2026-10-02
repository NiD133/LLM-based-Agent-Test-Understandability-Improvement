package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_allowsSpaceInUrl {

    @Test
    void allowsSpaceInUrl() {
        assertEquals("https://example.com/foo bar/", resolve("HTTPS://example.com/example/", "../foo bar/"));
    }
}
