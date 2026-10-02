package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ByteOrderMark#getBytes()}.
 */
public class ByteOrderMarkTest_testGetBytes {

    /** A BOM with a single byte: {1}. */
    private static final ByteOrderMark BOM_ONE_BYTE = new ByteOrderMark("test1", 1);

    /** A BOM with two bytes: {1, 2}. */
    private static final ByteOrderMark BOM_TWO_BYTES = new ByteOrderMark("test2", 1, 2);

    /** A BOM with three bytes: {1, 2, 3}. */
    private static final ByteOrderMark BOM_THREE_BYTES = new ByteOrderMark("test3", 1, 2, 3);

    /**
     * Verifies that {@link ByteOrderMark#getBytes()} returns the configured bytes,
     * and that the returned array is a defensive copy: mutating it must not change
     * what subsequent calls return.
     */
    @Test
    void testGetBytes() {
        // getBytes() reflects the bytes supplied to the constructor.
        assertArrayEquals(new byte[] { (byte) 1 }, BOM_ONE_BYTE.getBytes(), "single-byte BOM");

        // Mutating the returned array must not affect the BOM's internal state,
        // because getBytes() hands back a fresh copy each time.
        BOM_ONE_BYTE.getBytes()[0] = 2;
        assertArrayEquals(new byte[] { (byte) 1 }, BOM_ONE_BYTE.getBytes(),
                "single-byte BOM unchanged after mutating a returned copy");

        // getBytes() works for BOMs of arbitrary length.
        assertArrayEquals(new byte[] { (byte) 1, (byte) 2 }, BOM_TWO_BYTES.getBytes(), "two-byte BOM");
        assertArrayEquals(new byte[] { (byte) 1, (byte) 2, (byte) 3 }, BOM_THREE_BYTES.getBytes(), "three-byte BOM");
    }
}
