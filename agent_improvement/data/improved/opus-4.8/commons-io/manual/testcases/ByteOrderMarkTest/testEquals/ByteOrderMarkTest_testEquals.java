package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#equals(Object)}.
 * <p>
 * Two BOMs are equal when they have the same sequence of bytes; the charset
 * name is intentionally ignored by {@code equals}.
 * </p>
 */
public class ByteOrderMarkTest_testEquals {

    /** A BOM with the single byte sequence {1}. */
    private static final ByteOrderMark BOM_BYTES_1 = new ByteOrderMark("test1", 1);

    /** A BOM with the byte sequence {1, 2}. */
    private static final ByteOrderMark BOM_BYTES_1_2 = new ByteOrderMark("test2", 1, 2);

    /** A BOM with the byte sequence {1, 2, 3}. */
    private static final ByteOrderMark BOM_BYTES_1_2_3 = new ByteOrderMark("test3", 1, 2, 3);

    @SuppressWarnings("EqualsWithItself")
    @Test
    void testEquals() {
        // A predefined BOM is equal to itself.
        assertEquals(ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16BE);
        assertEquals(ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16LE);
        assertEquals(ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32BE);
        assertEquals(ByteOrderMark.UTF_32LE, ByteOrderMark.UTF_32LE);
        assertEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_8);

        // Different predefined BOMs have different bytes, so they are not equal.
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32BE);
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32LE);

        // A custom BOM is equal to itself.
        assertEquals(BOM_BYTES_1, BOM_BYTES_1, "test1 equals");
        assertEquals(BOM_BYTES_1_2, BOM_BYTES_1_2, "test2 equals");
        assertEquals(BOM_BYTES_1_2_3, BOM_BYTES_1_2_3, "test3 equals");

        // A BOM is never equal to a non-BOM object.
        assertNotEquals(BOM_BYTES_1, new Object(), "Object not equal");

        // Different byte sequences are not equal, regardless of charset name.
        assertNotEquals(BOM_BYTES_1, new ByteOrderMark("1a", 2), "test1-1 not equal");
        assertNotEquals(BOM_BYTES_1, new ByteOrderMark("1b", 1, 2), "test1-2 not test2");
        assertNotEquals(BOM_BYTES_1_2, new ByteOrderMark("2", 1, 1), "test2 not equal");
        assertNotEquals(BOM_BYTES_1_2_3, new ByteOrderMark("3", 1, 2, 4), "test3 not equal");
    }
}
