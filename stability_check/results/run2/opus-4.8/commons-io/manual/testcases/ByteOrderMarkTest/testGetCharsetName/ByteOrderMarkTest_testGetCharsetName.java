package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#getCharsetName()}.
 */
public class ByteOrderMarkTest_testGetCharsetName {

    /** A BOM built from a single byte, named "test1". */
    private static final ByteOrderMark BOM_ONE_BYTE = new ByteOrderMark("test1", 1);

    /** A BOM built from two bytes, named "test2". */
    private static final ByteOrderMark BOM_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    /** A BOM built from three bytes, named "test3". */
    private static final ByteOrderMark BOM_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Verifies that {@link ByteOrderMark#getCharsetName()} returns the charset
     * name supplied to the constructor, regardless of how many bytes the BOM has.
     */
    @Test
    void testGetCharsetName() {
        assertEquals("test1", BOM_ONE_BYTE.getCharsetName(), "single-byte BOM charset name");
        assertEquals("test2", BOM_TWO_BYTES.getCharsetName(), "two-byte BOM charset name");
        assertEquals("test3", BOM_THREE_BYTES.getCharsetName(), "three-byte BOM charset name");
    }
}
