package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForUpperLimit {

    private static final String NO_SUFFIX = "";

    @Test
    void testAbbreviateForUpperLimit() {
        assertEquals("01234", WordUtils.abbreviate("0123456789", 0, 5, NO_SUFFIX));
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, NO_SUFFIX));
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, NO_SUFFIX));
    }
}
