package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithInvalidOffset {

    private static final byte[] EXPECTED_BYTES = { 2, 4, 6, 8, 10 };
    private static final int INVALID_OFFSET = -1;
    private static final int BYTES_TO_COMPARE = 5;
    private static final String INVALID_OFFSET_MESSAGE = "Illegal offset: -1";

    @Test
    void testPeekWithInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> buffer.peek(EXPECTED_BYTES, INVALID_OFFSET, BYTES_TO_COMPARE));

        assertEquals(INVALID_OFFSET_MESSAGE, exception.getMessage());
    }
}
