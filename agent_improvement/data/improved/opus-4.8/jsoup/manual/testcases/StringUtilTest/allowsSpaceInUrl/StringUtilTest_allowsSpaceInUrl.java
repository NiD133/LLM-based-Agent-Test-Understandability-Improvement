package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link StringUtil#resolve(String, String)} keeps a literal space
 * within a relative URL while resolving it against an absolute base.
 */
public class StringUtilTest_allowsSpaceInUrl {

    @Test
    void resolveKeepsSpaceInRelativeUrl() {
        // The base URL uses an uppercase scheme and includes a path segment ("example/")
        // that the "../" in the relative URL should walk back out of.
        String absoluteBase = "HTTPS://example.com/example/";
        String relativeWithSpace = "../foo bar/";

        String resolved = resolve(absoluteBase, relativeWithSpace);

        // The scheme is normalised to lowercase, "example/" is removed by "../",
        // and the space in "foo bar" is preserved.
        assertEquals("https://example.com/foo bar/", resolved);
    }
}
