package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetCharsetName {

    // Single-byte BOM with charset name "test1"
    private static final ByteOrderMark SINGLE_BYTE_BOM = new ByteOrderMark("test1", 1);

    // Two-byte BOM with charset name "test2"
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);

    // Three-byte BOM with charset name "test3"
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Verifies that {@link ByteOrderMark#getCharsetName()} returns the charset name
     * that was supplied to the constructor, regardless of how many BOM bytes were provided.
     */
    @Test
    void testGetCharsetName() {
        assertEquals("test1", SINGLE_BYTE_BOM.getCharsetName(),
                "getCharsetName() should return the charset name passed to the constructor (1-byte BOM)");
        assertEquals("test2", TWO_BYTE_BOM.getCharsetName(),
                "getCharsetName() should return the charset name passed to the constructor (2-byte BOM)");
        assertEquals("test3", THREE_BYTE_BOM.getCharsetName(),
                "getCharsetName() should return the charset name passed to the constructor (3-byte BOM)");
    }
}
