package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Tests that CircularByteBuffer.add(byte[], int, int) rejects a negative length argument.
 */
public class CircularByteBufferTest_testAddNegativeLength {

    @Test
    void testAddNegativeLength() {
        // A fresh buffer with default capacity
        final CircularByteBuffer buffer = new CircularByteBuffer();

        // Source data — the actual content doesn't matter; the invalid length should be caught first
        final byte[] sourceData = {1, 2, 3};

        // Passing a negative length must throw IllegalArgumentException before any bytes are copied
        assertThrows(
            IllegalArgumentException.class,
            () -> buffer.add(sourceData, /* offset= */ 0, /* length= */ -1),
            "add() should throw IllegalArgumentException when length is negative"
        );
    }
}
