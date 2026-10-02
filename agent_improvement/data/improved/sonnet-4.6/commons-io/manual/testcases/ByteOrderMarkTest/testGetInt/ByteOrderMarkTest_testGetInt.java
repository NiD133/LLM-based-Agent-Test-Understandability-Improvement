package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetInt {

    /** BOM with a single byte: [0x01] */
    private static final ByteOrderMark BOM_ONE_BYTE = new ByteOrderMark("test1", 1);

    /** BOM with two bytes: [0x01, 0x02] */
    private static final ByteOrderMark BOM_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    /** BOM with three bytes: [0x01, 0x02, 0x03] */
    private static final ByteOrderMark BOM_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#get(int)} for BOMs of varying lengths.
     * Each BOM stores bytes [1], [1,2], and [1,2,3] respectively; get(pos)
     * must return the value at the given zero-based index.
     */
    @Test
    void testGetInt() {
        // Single-byte BOM: only index 0 is valid
        assertEquals(1, BOM_ONE_BYTE.get(0), "test1 get(0)");

        // Two-byte BOM: indices 0 and 1
        assertEquals(1, BOM_TWO_BYTES.get(0), "test2 get(0)");
        assertEquals(2, BOM_TWO_BYTES.get(1), "test2 get(1)");

        // Three-byte BOM: indices 0, 1, and 2
        assertEquals(1, BOM_THREE_BYTES.get(0), "test3 get(0)");
        assertEquals(2, BOM_THREE_BYTES.get(1), "test3 get(1)");
        assertEquals(3, BOM_THREE_BYTES.get(2), "test3 get(2)");
    }
}
