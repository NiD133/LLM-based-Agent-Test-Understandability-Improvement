package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForLowerValueAndAppendedString {

    @Test
    void testAbbreviateForLowerValueAndAppendedString() {
        final String textWithFirstSpaceAfterLowerLimit = "012 3456789";
        assertEquals("012", WordUtils.abbreviate(textWithFirstSpaceAfterLowerLimit, 0, 5, null));

        final String textWithFirstSpaceAtLowerLimit = "01234 56789";
        assertEquals("01234-", WordUtils.abbreviate(textWithFirstSpaceAtLowerLimit, 5, 10, "-"));

        final String textWithNoUpperLimit = "01 23 45 67 89";
        assertEquals("01 23 45 67abc", WordUtils.abbreviate(textWithNoUpperLimit, 9, -1, "abc"));

        final String textWithEmptyAppendString = "01 23 45 67 89";
        assertEquals("01 23 45 6", WordUtils.abbreviate(textWithEmptyAppendString, 9, 10, ""));
    }
}
