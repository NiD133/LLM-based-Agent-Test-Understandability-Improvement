package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetInt {

    private static final ByteOrderMark ONE_BYTE_BOM = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TWO_BYTE_BOM = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark THREE_BYTE_BOM = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#get(int)} for each byte position in the sample BOMs.
     */
    @Test
    void testGetInt() {
        assertByteAtPosition(ONE_BYTE_BOM, 0, 1, "test1 get(0)");

        assertByteAtPosition(TWO_BYTE_BOM, 0, 1, "test2 get(0)");
        assertByteAtPosition(TWO_BYTE_BOM, 1, 2, "test2 get(1)");

        assertByteAtPosition(THREE_BYTE_BOM, 0, 1, "test3 get(0)");
        assertByteAtPosition(THREE_BYTE_BOM, 1, 2, "test3 get(1)");
        assertByteAtPosition(THREE_BYTE_BOM, 2, 3, "test3 get(2)");
    }

    private static void assertByteAtPosition(final ByteOrderMark byteOrderMark, final int position, final int expectedByte,
            final String message) {
        assertEquals(expectedByte, byteOrderMark.get(position), message);
    }
}
