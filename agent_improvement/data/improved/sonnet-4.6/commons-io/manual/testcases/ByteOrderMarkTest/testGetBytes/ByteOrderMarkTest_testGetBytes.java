package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testGetBytes {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);

    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);

    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Tests {@link ByteOrderMark#getBytes()} returns a defensive copy of the BOM bytes.
     * Mutating the returned array must not affect subsequent calls.
     */
    @Test
    void testGetBytes() {
        assertArrayEquals(new byte[] { (byte) 1 }, TEST_BOM_1.getBytes(), "test1 bytes");

        // Verify getBytes() returns a defensive copy: modifying the array must not alter the BOM
        byte[] mutableCopy = TEST_BOM_1.getBytes();
        mutableCopy[0] = 2;
        assertArrayEquals(new byte[] { (byte) 1 }, TEST_BOM_1.getBytes(), "test1 bytes after mutation");

        assertArrayEquals(new byte[] { (byte) 1, (byte) 2 }, TEST_BOM_2.getBytes(), "test2 bytes");
        assertArrayEquals(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, TEST_BOM_3.getBytes(), "test3 bytes");
    }
}
