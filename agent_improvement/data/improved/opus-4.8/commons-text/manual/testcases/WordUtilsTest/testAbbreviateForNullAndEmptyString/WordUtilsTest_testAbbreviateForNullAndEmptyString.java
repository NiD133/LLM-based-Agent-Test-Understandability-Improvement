package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link WordUtils#abbreviate(String, int, int, String)} handles
 * inputs that are either {@code null}, empty, or collapse to an empty result.
 *
 * <p>Per the contract of {@code abbreviate}: a {@code null} input yields
 * {@code null}, and an empty input yields the empty String. When the upper limit
 * forces the result down to zero characters, the result is also empty.</p>
 */
public class WordUtilsTest_testAbbreviateForNullAndEmptyString {

    /** The append-to-end marker; empty so it never adds characters to the result. */
    private static final String NO_APPEND = "";

    @Test
    void testAbbreviateForNullAndEmptyString() {
        // A null input string is returned unchanged as null.
        assertNull(WordUtils.abbreviate(null, 1, -1, NO_APPEND));

        // An empty input string is returned unchanged as the empty string.
        assertEquals(StringUtils.EMPTY, WordUtils.abbreviate("", 1, -1, NO_APPEND));

        // upper == 0 forces the abbreviation down to zero characters.
        assertEquals("", WordUtils.abbreviate("0123456790", 0, 0, NO_APPEND));

        // A leading space is found at index 0 (>= lower 0), so the result is empty.
        assertEquals("", WordUtils.abbreviate(" 0123456790", 0, -1, NO_APPEND));
    }
}
