package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ByteOrderMark#getCharsetName()} returns the charset name
 * supplied to the constructor, independently of how many bytes the BOM holds.
 */
public class ByteOrderMarkTest_testGetCharsetName {

    /** A BOM carrying one byte, constructed with charset name "test1". */
    private static final ByteOrderMark ONE_BYTE_BOM = new ByteOrderMark("test1", 1);

    /** A BOM carrying two bytes, constructed with charset name "test2". */
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);

    /** A BOM carrying three bytes, constructed with charset name "test3". */
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    void testGetCharsetName() {
        assertEquals("test1", ONE_BYTE_BOM.getCharsetName(), "charset name of one-byte BOM");
        assertEquals("test2", TWO_BYTE_BOM.getCharsetName(), "charset name of two-byte BOM");
        assertEquals("test3", THREE_BYTE_BOM.getCharsetName(), "charset name of three-byte BOM");
    }
}
