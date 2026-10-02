package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testMatches {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);

    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);

    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Verifies that each standard BOM constant matches its own raw bytes exactly.
     */
    @Test
    void testMatchesStandardBomConstants() {
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_16LE.matches(ByteOrderMark.UTF_16LE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_32BE.matches(ByteOrderMark.UTF_32BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_8.matches(ByteOrderMark.UTF_8.getRawBytes()));
    }

    /**
     * Verifies that each custom BOM matches its own raw bytes exactly.
     */
    @Test
    void testMatchesCustomBomsSelf() {
        assertTrue(TEST_BOM_1.matches(TEST_BOM_1.getRawBytes()));
        assertTrue(TEST_BOM_2.matches(TEST_BOM_2.getRawBytes()));
        assertTrue(TEST_BOM_3.matches(TEST_BOM_3.getRawBytes()));
    }

    /**
     * Verifies prefix-matching behaviour: a BOM matches any array whose leading
     * bytes are identical to the BOM's bytes, even if the array is longer.
     *
     * TEST_BOM_1 has bytes [1]. An input array [1, 2] starts with 1, so it matches.
     */
    @Test
    void testMatchesReturnsTrueForArrayWithMatchingPrefix() {
        ByteOrderMark longerArrayStartingWithBom1Bytes = new ByteOrderMark("1b", 1, 2);
        assertTrue(TEST_BOM_1.matches(longerArrayStartingWithBom1Bytes.getRawBytes()));
    }

    /**
     * Verifies that a BOM does NOT match when its bytes differ from the input.
     *
     * Cases covered:
     *  - TEST_BOM_1 ([1]) vs [2]           — first byte differs
     *  - TEST_BOM_2 ([1,2]) vs [1,1]       — second byte differs
     *  - TEST_BOM_3 ([1,2,3]) vs [1,2,4]   — third byte differs
     */
    @Test
    void testMatchesReturnsFalseForDifferentBytes() {
        ByteOrderMark differentFirstByte   = new ByteOrderMark("1a", 2);
        ByteOrderMark differentSecondByte  = new ByteOrderMark("2",  1, 1);
        ByteOrderMark differentThirdByte   = new ByteOrderMark("3",  1, 2, 4);

        assertFalse(TEST_BOM_1.matches(differentFirstByte.getRawBytes()));
        assertFalse(TEST_BOM_2.matches(differentSecondByte.getRawBytes()));
        assertFalse(TEST_BOM_3.matches(differentThirdByte.getRawBytes()));
    }
}
