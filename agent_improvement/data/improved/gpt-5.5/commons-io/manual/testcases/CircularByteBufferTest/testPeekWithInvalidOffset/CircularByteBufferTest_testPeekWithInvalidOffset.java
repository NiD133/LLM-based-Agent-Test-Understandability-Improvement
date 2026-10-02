package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithInvalidOffset {

    @Test
    void testPeekWithInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] bytesToCompare = { 2, 4, 6, 8, 10 };
        final int invalidOffset = -1;
        final int length = 5;

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> buffer.peek(bytesToCompare, invalidOffset, length));

        assertEquals("Illegal offset: -1", exception.getMessage());
    }
}
