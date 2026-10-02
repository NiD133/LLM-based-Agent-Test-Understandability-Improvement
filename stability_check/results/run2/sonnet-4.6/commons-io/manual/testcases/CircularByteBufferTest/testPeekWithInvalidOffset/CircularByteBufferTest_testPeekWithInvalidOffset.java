package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithInvalidOffset {

    // peek() must reject a negative offset and report it in the exception message
    @Test
    void testPeekWithInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] sourceData = { 2, 4, 6, 8, 10 };
        final int negativeOffset = -1;
        final int length = 5;

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(sourceData, negativeOffset, length));

        assertEquals("Illegal offset: " + negativeOffset, exception.getMessage());
    }
}
