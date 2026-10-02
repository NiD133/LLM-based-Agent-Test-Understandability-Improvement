package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#abbreviate(String, int, int, String)}.
 *
 * <p>The method abbreviates text at the first space found at or after the
 * {@code lower} limit, but never lets the result grow past the {@code upper}
 * limit ({@code -1} means "no upper limit"). When (and only when) the text is
 * actually shortened, the {@code appendToEnd} string is added; a {@code null}
 * suffix is treated as an empty string.</p>
 */
public class WordUtilsTest_testAbbreviateForLowerValueAndAppendedString {

    @Test
    void testAbbreviateForLowerValueAndAppendedString() {
        // No space exists before the upper limit, so the text is cut at the
        // upper limit (5). A null suffix contributes nothing.
        assertEquals("012",
                WordUtils.abbreviate("012 3456789", 0, 5, null));

        // First space at/after lower (5) is found; text is cut there and the
        // "-" suffix is appended because abbreviation occurred.
        assertEquals("01234-",
                WordUtils.abbreviate("01234 56789", 5, 10, "-"));

        // upper = -1 means no upper limit: cut at the first space at/after
        // lower (9) and append the "abc" suffix.
        assertEquals("01 23 45 67abc",
                WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc"));

        // The upper limit (10) is reached before the next space, so the text is
        // cut at 10 characters and an empty suffix adds nothing.
        assertEquals("01 23 45 6",
                WordUtils.abbreviate("01 23 45 67 89", 9, 10, ""));
    }
}
