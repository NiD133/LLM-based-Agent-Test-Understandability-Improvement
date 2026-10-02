package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#matches(int[])}.
 * <p>
 * {@code matches} returns {@code true} when the given array <em>starts with</em>
 * this BOM's bytes (the array may be longer), and {@code false} otherwise.
 * </p>
 */
public class ByteOrderMarkTest_testMatches {

    /** BOM whose bytes are {@code [1]}. */
    private static final ByteOrderMark BOM_1 = new ByteOrderMark("test1", 1);

    /** BOM whose bytes are {@code [1, 2]}. */
    private static final ByteOrderMark BOM_1_2 = new ByteOrderMark("test2", 1, 2);

    /** BOM whose bytes are {@code [1, 2, 3]}. */
    private static final ByteOrderMark BOM_1_2_3 = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testMatches() {
        // A BOM always matches its own raw bytes - this holds for the predefined
        // standard BOMs as well as for custom ones.
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_16LE.matches(ByteOrderMark.UTF_16LE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_32BE.matches(ByteOrderMark.UTF_32BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
        assertTrue(ByteOrderMark.UTF_8.matches(ByteOrderMark.UTF_8.getRawBytes()));
        assertTrue(BOM_1.matches(BOM_1.getRawBytes()));
        assertTrue(BOM_1_2.matches(BOM_1_2.getRawBytes()));
        assertTrue(BOM_1_2_3.matches(BOM_1_2_3.getRawBytes()));

        // [1] does not match [2]: the single byte differs.
        assertFalse(BOM_1.matches(new ByteOrderMark("1a", 2).getRawBytes()));

        // [1] matches [1, 2]: the array starts with [1], extra trailing bytes are allowed.
        assertTrue(BOM_1.matches(new ByteOrderMark("1b", 1, 2).getRawBytes()));

        // [1, 2] does not match [1, 1]: the second byte differs.
        assertFalse(BOM_1_2.matches(new ByteOrderMark("2", 1, 1).getRawBytes()));

        // [1, 2, 3] does not match [1, 2, 4]: the third byte differs.
        assertFalse(BOM_1_2_3.matches(new ByteOrderMark("3", 1, 2, 4).getRawBytes()));
    }
}
