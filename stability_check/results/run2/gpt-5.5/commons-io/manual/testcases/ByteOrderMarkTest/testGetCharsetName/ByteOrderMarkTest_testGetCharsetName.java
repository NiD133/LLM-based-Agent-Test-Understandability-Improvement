package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetCharsetName {

    private static final ByteOrderMark ONE_BYTE_BOM = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Verifies that {@link ByteOrderMark#getCharsetName()} returns the charset
     * name supplied when the byte order mark was created.
     */
    @Test
    void testGetCharsetName() {
        assertEquals("test1", ONE_BYTE_BOM.getCharsetName(), "test1 name");
        assertEquals("test2", TWO_BYTE_BOM.getCharsetName(), "test2 name");
        assertEquals("test3", THREE_BYTE_BOM.getCharsetName(), "test3 name");
    }
}
