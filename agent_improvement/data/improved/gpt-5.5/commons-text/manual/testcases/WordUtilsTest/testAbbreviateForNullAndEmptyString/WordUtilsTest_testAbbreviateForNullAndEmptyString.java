package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForNullAndEmptyString {

    @Test
    void testAbbreviateForNullAndEmptyString() {
        assertNull(WordUtils.abbreviate(null, 1, -1, ""));
        assertEquals(StringUtils.EMPTY, WordUtils.abbreviate("", 1, -1, ""));

        assertEquals("", WordUtils.abbreviate("0123456790", 0, 0, ""));
        assertEquals("", WordUtils.abbreviate(" 0123456790", 0, -1, ""));
    }
}
