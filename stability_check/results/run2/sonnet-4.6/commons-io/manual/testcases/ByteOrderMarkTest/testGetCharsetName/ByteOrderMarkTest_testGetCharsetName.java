package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ByteOrderMark#getCharsetName()} returns the charset name
 * that was supplied to the constructor.
 */
public class ByteOrderMarkTest_testGetCharsetName {

    // Single-byte BOM with charset name "test1"
    private static final ByteOrderMark BOM_SINGLE_BYTE = new ByteOrderMark("test1", 1);

    // Two-byte BOM with charset name "test2"
    private static final ByteOrderMark BOM_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    // Three-byte BOM with charset name "test3"
    private static final ByteOrderMark BOM_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    @DisplayName("getCharsetName() returns the charset name provided at construction time")
    void testGetCharsetName() {
        assertEquals("test1", BOM_SINGLE_BYTE.getCharsetName(),
                "Single-byte BOM should return the charset name 'test1' given at construction");
        assertEquals("test2", BOM_TWO_BYTES.getCharsetName(),
                "Two-byte BOM should return the charset name 'test2' given at construction");
        assertEquals("test3", BOM_THREE_BYTES.getCharsetName(),
                "Three-byte BOM should return the charset name 'test3' given at construction");
    }
}
