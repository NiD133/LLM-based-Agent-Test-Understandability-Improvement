package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#abbreviate(String, int, int, String)}.
 *
 * <p>{@code abbreviate} shortens a string so that it ends on a word boundary
 * (a space). It scans from the {@code lower} index forward to the next space and
 * truncates there; if {@code upper} is reached first (or no space is found before
 * {@code upper}), it truncates at {@code upper} instead. An {@code upper} of -1
 * means "no upper limit". The {@code appendToEnd} suffix (here always {@code null})
 * is added only when the string is actually shortened.</p>
 */
public class WordUtilsTest_testAbbreviateForLowerValue {

    @Test
    void testAbbreviateForLowerValue() {
        // No space exists before the lower index 0, so the first space found
        // (after "012") sits within [lower, upper) and becomes the cut point.
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));

        // The space at index 5 is exactly at the lower limit, so the result is
        // truncated there, keeping the first word "01234".
        assertEquals("01234", WordUtils.abbreviate("01234 56789", 5, 10, null));

        // upper == -1 means no upper bound: scan from index 9 to the next space
        // (between "67" and "89") and cut there.
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 9, -1, null));

        // With upper == 10 the limit is hit before the next space, so the string
        // is cut at index 10 ("01 23 45 6").
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, null));

        // lower (15) exceeds the string length, so the whole string is returned
        // unchanged and no suffix is appended.
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 15, 20, null));
    }
}
