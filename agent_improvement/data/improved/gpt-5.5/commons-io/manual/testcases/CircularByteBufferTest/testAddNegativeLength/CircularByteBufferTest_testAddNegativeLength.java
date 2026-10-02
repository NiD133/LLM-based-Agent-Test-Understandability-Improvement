package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddNegativeLength {

    @Test
    void testAddNegativeLength() {
        final CircularByteBuffer circularByteBuffer = new CircularByteBuffer();
        final byte[] targetBuffer = { 1, 2, 3 };
        final int validOffset = 0;
        final int negativeLength = -1;

        assertThrows(IllegalArgumentException.class,
                () -> circularByteBuffer.add(targetBuffer, validOffset, negativeLength));
    }
}
