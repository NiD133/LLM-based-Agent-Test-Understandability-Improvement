package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetCharsetName {

    // BOMs with different byte-lengths to verify getCharsetName() is independent of BOM width
    private static final ByteOrderMark SINGLE_BYTE_BOM = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark DOUBLE_BYTE_BOM = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TRIPLE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests that {@link ByteOrderMark#getCharsetName()} returns the charset name supplied at
     * construction time, regardless of how many bytes the BOM contains.
     */
    @Test
    void testGetCharsetName() {
        assertEquals("test1", SINGLE_BYTE_BOM.getCharsetName(),
                "1-byte BOM should return the charset name given at construction");
        assertEquals("test2", DOUBLE_BYTE_BOM.getCharsetName(),
                "2-byte BOM should return the charset name given at construction");
        assertEquals("test3", TRIPLE_BYTE_BOM.getCharsetName(),
                "3-byte BOM should return the charset name given at construction");
    }
}
