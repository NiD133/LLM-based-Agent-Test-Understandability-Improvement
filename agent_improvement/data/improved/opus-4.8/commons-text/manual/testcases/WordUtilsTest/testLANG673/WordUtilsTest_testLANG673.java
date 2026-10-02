package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Regression test for LANG-673.
 *
 * <p>Verifies {@link WordUtils#abbreviate(String, int, int, String)} when the empty
 * string is used as the {@code appendToEnd} marker. The abbreviation should break at
 * the first space at or after the lower limit, and never exceed the upper limit.</p>
 */
public class WordUtilsTest_testLANG673 {

    /** The text abbreviated in every case below: five two-digit words separated by spaces. */
    private static final String INPUT = "01 23 45 67 89";

    /** Empty marker: nothing is appended even when the text is abbreviated. */
    private static final String NO_APPENDED_MARKER = "";

    @Test
    void testLANG673() {
        // Lower limit 0: break at the first space, keeping only the first word.
        assertEquals("01",
                WordUtils.abbreviate(INPUT, 0, 40, NO_APPENDED_MARKER));

        // Lower limit 10: break at the first space at or after index 10.
        assertEquals("01 23 45 67",
                WordUtils.abbreviate(INPUT, 10, 40, NO_APPENDED_MARKER));

        // Lower limit 40 exceeds the text length: the whole text is returned unchanged.
        assertEquals("01 23 45 67 89",
                WordUtils.abbreviate(INPUT, 40, 40, NO_APPENDED_MARKER));
    }
}
