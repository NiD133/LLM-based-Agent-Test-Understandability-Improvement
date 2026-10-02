package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testToString {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#toString()}.
     */
    @Test
    void testToString() {
        assertToString("ByteOrderMark[test1: 0x1]", TEST_BOM_1, "test1 ");
        assertToString("ByteOrderMark[test2: 0x1,0x2]", TEST_BOM_2, "test2 ");
        assertToString("ByteOrderMark[test3: 0x1,0x2,0x3]", TEST_BOM_3, "test3 ");
    }

    private void assertToString(final String expected, final ByteOrderMark byteOrderMark, final String message) {
        assertEquals(expected, byteOrderMark.toString(), message);
    }
}
