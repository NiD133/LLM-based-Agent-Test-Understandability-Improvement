package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link WordUtils#abbreviate(String, int, int, String)} applies its
 * {@code upper} bound, which caps where the abbreviated text may be cut off.
 */
public class WordUtilsTest_testAbbreviateForUpperLimit {

    /** Empty marker so the result holds only the abbreviated text, with nothing appended. */
    private static final String NO_APPENDED_MARKER = "";

    @Test
    void testAbbreviateForUpperLimit() {
        // No word boundary before the upper bound, so the text is cut at the upper bound (index 5).
        assertEquals("01234",
                WordUtils.abbreviate("0123456789", 0, 5, NO_APPENDED_MARKER));

        // A space falls between the lower bound (2) and the upper bound (5),
        // so the text is cut at that space, yielding the first word.
        assertEquals("012",
                WordUtils.abbreviate("012 3456789", 2, 5, NO_APPENDED_MARKER));

        // An upper bound of -1 means "no upper limit", so the full text is returned.
        assertEquals("0123456789",
                WordUtils.abbreviate("0123456789", 0, -1, NO_APPENDED_MARKER));
    }
}
