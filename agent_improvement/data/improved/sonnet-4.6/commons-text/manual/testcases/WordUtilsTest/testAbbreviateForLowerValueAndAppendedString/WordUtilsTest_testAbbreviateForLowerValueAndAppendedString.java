package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForLowerValueAndAppendedString {

    @Test
    void testAbbreviateForLowerValueAndAppendedString() {
        // Space found before upper limit; null appendToEnd means no suffix added
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null),
                "Should abbreviate at the first space within the upper limit when appendToEnd is null");

        // Space found exactly at the lower limit; custom append suffix is added
        assertEquals("01234-", WordUtils.abbreviate("01234 56789", 5, 10, "-"),
                "Should abbreviate at the space at the lower limit and append the given suffix");

        // No upper limit (upper=-1); appends suffix after last word boundary before end
        assertEquals("01 23 45 67abc", WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc"),
                "Should abbreviate at the first space past the lower limit when upper is -1 (no limit)");

        // Space found but capped by upper limit; empty appendToEnd means no visible suffix
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, ""),
                "Should truncate at the upper limit when the next space exceeds it and appendToEnd is empty");
    }
}
