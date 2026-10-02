package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testLength {

    private static final ByteOrderMark ONE_BYTE_BOM = new ByteOrderMark("test1", 1);

    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);

    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#length()} returns the number of bytes in the BOM.
     */
    @Test
    void testLength() {
        assertEquals(1, ONE_BYTE_BOM.length(), "test1 length");
        assertEquals(2, TWO_BYTE_BOM.length(), "test2 length");
        assertEquals(3, THREE_BYTE_BOM.length(), "test3 length");
    }
}
