package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalize_String {

    @Test
    void testCapitalize_nullReturnsNull() {
        assertNull(WordUtils.capitalize(null));
    }

    @Test
    void testCapitalize_emptyAndWhitespaceStringsAreReturnedUnchanged() {
        assertEquals("", WordUtils.capitalize(""));
        assertEquals("  ", WordUtils.capitalize("  "));
    }

    @Test
    void testCapitalize_singleLetterWordIsCapitalized() {
        assertEquals("I", WordUtils.capitalize("I"));
        assertEquals("I", WordUtils.capitalize("i"));
    }

    @Test
    void testCapitalize_onlyFirstLetterOfEachWordIsChangedToTitleCase() {
        // all-lowercase input: each word's first letter becomes uppercase
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));
        // already-capitalized input: no change expected
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));
        // mixed case within words: only the first letter of each word changes
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
        // fully-uppercase words: only the first letter is preserved as-is, rest unchanged
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }
}
