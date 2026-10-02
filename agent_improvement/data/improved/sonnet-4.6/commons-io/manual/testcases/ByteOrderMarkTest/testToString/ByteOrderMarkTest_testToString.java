package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testToString {

    /** A BOM with a single byte value (0x1). */
    private static final ByteOrderMark SINGLE_BYTE_BOM = new ByteOrderMark("test1", 1);

    /** A BOM with two byte values (0x1, 0x2). */
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);

    /** A BOM with three byte values (0x1, 0x2, 0x3). */
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#toString()} produces the expected
     * "ByteOrderMark[charsetName: 0xHEX,...]" format for BOMs with
     * one, two, and three bytes respectively.
     */
    @Test
    void testToString() {
        assertEquals("ByteOrderMark[test1: 0x1]", SINGLE_BYTE_BOM.toString(),
                "Single-byte BOM toString should list one hex value");
        assertEquals("ByteOrderMark[test2: 0x1,0x2]", TWO_BYTE_BOM.toString(),
                "Two-byte BOM toString should list two comma-separated hex values");
        assertEquals("ByteOrderMark[test3: 0x1,0x2,0x3]", THREE_BYTE_BOM.toString(),
                "Three-byte BOM toString should list three comma-separated hex values");
    }
}
