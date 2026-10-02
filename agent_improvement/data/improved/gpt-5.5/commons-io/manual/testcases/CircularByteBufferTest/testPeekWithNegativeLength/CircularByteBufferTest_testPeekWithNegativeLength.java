package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithNegativeLength {

    @Test
    void testPeekWithNegativeLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] expectedBytes = { 1, 4, 3 };
        final int offset = 0;
        final int negativeLength = -1;
        final String expectedMessage = "Illegal length: -1";

        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> buffer.peek(expectedBytes, offset, negativeLength));

        assertEquals(expectedMessage, thrown.getMessage());
    }
}
