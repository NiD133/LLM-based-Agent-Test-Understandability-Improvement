package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithInvalidOffset {

    @Test
    void testPeekWithInvalidOffset() {
        // Arrange
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] sourceData = { 2, 4, 6, 8, 10 };
        final int negativeOffset = -1;
        final int length = 5;

        // Act & Assert: peek() must reject a negative offset before touching buffer data
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(sourceData, negativeOffset, length));

        assertEquals("Illegal offset: " + negativeOffset, thrown.getMessage());
    }
}
