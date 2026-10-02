package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#abbreviate(String, int, int, String)}, focusing on how the
 * {@code upper} limit and the appended marker interact.
 *
 * <p>The method signature is {@code abbreviate(str, lower, upper, appendToEnd)}:</p>
 * <ul>
 *   <li>{@code lower} – the minimum number of characters to include.</li>
 *   <li>{@code upper} – the maximum number of characters to include, or {@code -1} for no maximum.</li>
 *   <li>{@code appendToEnd} – text appended when the string is actually abbreviated.</li>
 * </ul>
 */
public class WordUtilsTest_testAbbreviateForUpperLimitAndAppendedString {

    @Test
    void testAbbreviateForUpperLimitAndAppendedString() {
        // Truncated at the upper limit of 5 characters, so the "-" marker is appended.
        assertEquals("01234-", WordUtils.abbreviate("0123456789", 0, 5, "-"));

        // The first space at or before the upper limit (5) ends the result; no marker is appended.
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, null));

        // An upper limit of -1 means "no maximum", so the whole string is returned unchanged.
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }
}
