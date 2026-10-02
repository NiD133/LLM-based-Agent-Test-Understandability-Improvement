package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetBytes {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);
    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);
    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    private static final byte[] TEST_BOM_1_BYTES = { (byte) 1 };
    private static final byte[] TEST_BOM_2_BYTES = { (byte) 1, (byte) 2 };
    private static final byte[] TEST_BOM_3_BYTES = { (byte) 1, (byte) 2, (byte) 3 };

    /**
     * Tests {@link ByteOrderMark#getBytes()}.
     */
    @Test
    void testGetBytes() {
        assertArrayEquals(TEST_BOM_1.getBytes(), TEST_BOM_1_BYTES, "test1 bytes");

        final byte[] mutableCopy = TEST_BOM_1.getBytes();
        mutableCopy[0] = 2;
        assertArrayEquals(TEST_BOM_1.getBytes(), TEST_BOM_1_BYTES, "test1 bytes");

        assertArrayEquals(TEST_BOM_2.getBytes(), TEST_BOM_2_BYTES, "test1 bytes");
        assertArrayEquals(TEST_BOM_3.getBytes(), TEST_BOM_3_BYTES, "test1 bytes");
    }
}
