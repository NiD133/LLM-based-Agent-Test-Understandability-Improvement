package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForNullAndEmptyString {

    @Test
    void testAbbreviateForNullAndEmptyString() {
        // null input returns null regardless of bounds
        assertNull(WordUtils.abbreviate(null, 1, -1, ""));

        // empty input returns empty string
        assertEquals("", WordUtils.abbreviate("", 1, -1, ""));

        // lower=0, upper=0: abbreviate to zero characters yields empty string
        assertEquals("", WordUtils.abbreviate("0123456790", 0, 0, ""));

        // string starting with a space, lower=0, upper=-1 (no upper limit):
        // abbreviates at the leading space, yielding empty string
        assertEquals("", WordUtils.abbreviate(" 0123456790", 0, -1, ""));
    }
}
