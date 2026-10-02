package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkIndexOf_functionality {

    private static final String ALPHABET_PREFIX = "ABCDEFGHIJ";

    @Test
    void test_checkIndexOf_functionality() {
        assertMatchesAtTheStart();
        assertMatchesInTheMiddle();
        assertMatchesAtTheEnd();
        assertMissingOrInvalidSearchesReturnNegativeOne();
    }

    private void assertMatchesAtTheStart() {
        assertSensitiveIndexOf(0, ALPHABET_PREFIX, 0, "A");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 1, "A");
        assertSensitiveIndexOf(0, ALPHABET_PREFIX, 0, "AB");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 1, "AB");
        assertSensitiveIndexOf(0, ALPHABET_PREFIX, 0, "ABC");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 1, "ABC");
    }

    private void assertMatchesInTheMiddle() {
        assertSensitiveIndexOf(3, ALPHABET_PREFIX, 0, "D");
        assertSensitiveIndexOf(3, ALPHABET_PREFIX, 3, "D");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 4, "D");
        assertSensitiveIndexOf(3, ALPHABET_PREFIX, 0, "DE");
        assertSensitiveIndexOf(3, ALPHABET_PREFIX, 3, "DE");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 4, "DE");
        assertSensitiveIndexOf(3, ALPHABET_PREFIX, 0, "DEF");
        assertSensitiveIndexOf(3, ALPHABET_PREFIX, 3, "DEF");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 4, "DEF");
    }

    private void assertMatchesAtTheEnd() {
        assertSensitiveIndexOf(9, ALPHABET_PREFIX, 0, "J");
        assertSensitiveIndexOf(9, ALPHABET_PREFIX, 8, "J");
        assertSensitiveIndexOf(9, ALPHABET_PREFIX, 9, "J");
        assertSensitiveIndexOf(8, ALPHABET_PREFIX, 0, "IJ");
        assertSensitiveIndexOf(8, ALPHABET_PREFIX, 8, "IJ");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 9, "IJ");
        assertSensitiveIndexOf(7, ALPHABET_PREFIX, 6, "HIJ");
        assertSensitiveIndexOf(7, ALPHABET_PREFIX, 7, "HIJ");
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 8, "HIJ");
    }

    private void assertMissingOrInvalidSearchesReturnNegativeOne() {
        assertSensitiveIndexOf(-1, ALPHABET_PREFIX, 0, "DED");
        assertSensitiveIndexOf(-1, "DEF", 0, ALPHABET_PREFIX);
        assertSensitiveIndexOf(-1, "ABC", 0, null);
        assertSensitiveIndexOf(-1, null, 0, "ABC");
        assertSensitiveIndexOf(-1, null, 0, null);
    }

    private void assertSensitiveIndexOf(final int expectedIndex, final String input, final int startIndex, final String search) {
        assertEquals(expectedIndex, IOCase.SENSITIVE.checkIndexOf(input, startIndex, search));
    }
}
