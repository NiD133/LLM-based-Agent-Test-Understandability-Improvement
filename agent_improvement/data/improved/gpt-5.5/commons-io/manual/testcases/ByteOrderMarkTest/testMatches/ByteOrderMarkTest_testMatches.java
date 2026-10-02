package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testMatches {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testMatches() {
        assertMatchesItsOwnRawBytes(ByteOrderMark.UTF_16BE);
        assertMatchesItsOwnRawBytes(ByteOrderMark.UTF_16LE);
        assertMatchesItsOwnRawBytes(ByteOrderMark.UTF_32BE);
        assertMatchesItsOwnRawBytes(ByteOrderMark.UTF_16BE);
        assertMatchesItsOwnRawBytes(ByteOrderMark.UTF_8);
        assertMatchesItsOwnRawBytes(TEST_BOM_1);
        assertMatchesItsOwnRawBytes(TEST_BOM_2);
        assertMatchesItsOwnRawBytes(TEST_BOM_3);

        assertDoesNotMatch(TEST_BOM_1, new ByteOrderMark("1a", 2));
        assertMatches(TEST_BOM_1, new ByteOrderMark("1b", 1, 2));
        assertDoesNotMatch(TEST_BOM_2, new ByteOrderMark("2", 1, 1));
        assertDoesNotMatch(TEST_BOM_3, new ByteOrderMark("3", 1, 2, 4));
    }

    private static void assertMatchesItsOwnRawBytes(final ByteOrderMark byteOrderMark) {
        assertTrue(byteOrderMark.matches(byteOrderMark.getRawBytes()));
    }

    private static void assertMatches(final ByteOrderMark expected, final ByteOrderMark candidate) {
        assertTrue(expected.matches(candidate.getRawBytes()));
    }

    private static void assertDoesNotMatch(final ByteOrderMark expected, final ByteOrderMark candidate) {
        assertFalse(expected.matches(candidate.getRawBytes()));
    }
}
