package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForUpperLimitAndAppendedString {

    @Test
    void testAbbreviateForUpperLimitAndAppendedString() {
        final String textWithoutSpaces = "0123456789";
        final String textWithSpaceInsideUpperLimit = "012 3456789";

        assertEquals("01234-", WordUtils.abbreviate(textWithoutSpaces, 0, 5, "-"));
        assertEquals("012", WordUtils.abbreviate(textWithSpaceInsideUpperLimit, 2, 5, null));
        assertEquals("0123456789", WordUtils.abbreviate(textWithoutSpaces, 0, -1, ""));
    }
}
