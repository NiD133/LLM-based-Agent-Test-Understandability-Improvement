package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#capitalize(String)}.
 *
 * <p>{@code capitalize(String)} title-cases the first character of every
 * whitespace-separated word and leaves the remaining characters untouched.</p>
 */
public class WordUtilsTest_testCapitalize_String {

    @Test
    void testCapitalize_String() {
        // A null input is returned unchanged as null.
        assertNull(WordUtils.capitalize(null));

        // Empty and whitespace-only inputs are returned unchanged.
        assertEquals("", WordUtils.capitalize(""));
        assertEquals("  ", WordUtils.capitalize("  "));

        // A single letter is title-cased regardless of its original case.
        assertEquals("I", WordUtils.capitalize("I"));
        assertEquals("I", WordUtils.capitalize("i"));

        // The first letter of each word is capitalized; digits are left as-is.
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));

        // Already-capitalized text is unchanged.
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));

        // Only the first letter of each word changes; the rest keep their case,
        // so an all-caps word stays all-caps.
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }
}
