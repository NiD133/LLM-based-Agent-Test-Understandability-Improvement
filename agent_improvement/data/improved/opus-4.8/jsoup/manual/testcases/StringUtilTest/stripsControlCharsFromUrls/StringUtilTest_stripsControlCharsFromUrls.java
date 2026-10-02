package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_stripsControlCharsFromUrls {

    /**
     * {@link StringUtil#resolve(String, String)} must strip ASCII control characters (0x00-0x1F)
     * from both the base and relative URLs before resolving, mirroring how browsers normalise URLs.
     *
     * <p>Here both inputs are peppered with control characters:
     * <ul>
     *   <li>base {@code "\nhttps://\texample.com/"} cleans to {@code "https://example.com/"}</li>
     *   <li>relative {@code "\r\nfo\to:ba\br"} cleans to {@code "foo:bar"}</li>
     * </ul>
     * Once the control characters are removed, {@code "foo:bar"} is already an absolute URL
     * (it has a scheme), so it resolves to itself.
     */
    @Test
    void stripsControlCharsFromUrls() {
        String baseWithControlChars = "\nhttps://\texample.com/";
        String relativeWithControlChars = "\r\nfo\to:ba\br";

        String resolved = resolve(baseWithControlChars, relativeWithControlChars);

        assertEquals("foo:bar", resolved);
    }
}
