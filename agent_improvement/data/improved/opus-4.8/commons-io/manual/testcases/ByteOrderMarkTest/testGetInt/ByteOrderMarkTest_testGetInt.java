package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#get(int)}.
 *
 * <p>A {@link ByteOrderMark} stores the bytes passed to its constructor. The
 * {@code get(int)} method returns the byte at a given zero-based position. These
 * tests verify that each stored byte can be read back at the position it was
 * supplied.</p>
 */
public class ByteOrderMarkTest_testGetInt {

    /** BOM holding a single byte: [1]. */
    private static final ByteOrderMark SINGLE_BYTE_BOM = new ByteOrderMark("test1", 1);

    /** BOM holding two bytes: [1, 2]. */
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);

    /** BOM holding three bytes: [1, 2, 3]. */
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testGetInt() {
        // Single-byte BOM: only position 0 is valid.
        assertEquals(1, SINGLE_BYTE_BOM.get(0), "SINGLE_BYTE_BOM.get(0)");

        // Two-byte BOM: positions 0 and 1 return the bytes in order.
        assertEquals(1, TWO_BYTE_BOM.get(0), "TWO_BYTE_BOM.get(0)");
        assertEquals(2, TWO_BYTE_BOM.get(1), "TWO_BYTE_BOM.get(1)");

        // Three-byte BOM: positions 0, 1 and 2 return the bytes in order.
        assertEquals(1, THREE_BYTE_BOM.get(0), "THREE_BYTE_BOM.get(0)");
        assertEquals(2, THREE_BYTE_BOM.get(1), "THREE_BYTE_BOM.get(1)");
        assertEquals(3, THREE_BYTE_BOM.get(2), "THREE_BYTE_BOM.get(2)");
    }
}
