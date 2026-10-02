package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForUpperLimit {

    @Test
    void testAbbreviateForUpperLimit() {
        // Upper limit forces truncation at exactly the upper-limit index when there is no space before it
        assertEquals("01234", WordUtils.abbreviate("0123456789", 0, 5, ""));

        // Upper limit forces truncation at the space that falls before the upper limit
        assertEquals("012", WordUtils.abbreviate("012 3456789", 2, 5, ""));

        // Upper limit of -1 means no limit: the full string is returned unchanged
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 0, -1, ""));
    }
}
