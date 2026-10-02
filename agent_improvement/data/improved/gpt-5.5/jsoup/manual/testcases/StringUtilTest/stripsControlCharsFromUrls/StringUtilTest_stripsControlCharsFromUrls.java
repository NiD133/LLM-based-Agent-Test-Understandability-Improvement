package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_stripsControlCharsFromUrls {

    @Test
    void stripsControlCharsFromUrls() {
        String baseUrlWithControlChars = "\nhttps://\texample.com/";
        String relativeUrlWithControlChars = "\r\nfo\to:ba\br";

        assertEquals("foo:bar", resolve(baseUrlWithControlChars, relativeUrlWithControlChars));
    }
}
