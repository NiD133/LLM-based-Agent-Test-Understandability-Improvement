package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithInvalidOffset {

    private static final byte[] SOURCE_BYTES = { 2, 4, 6, 8, 10 };
    private static final int INVALID_OFFSET = -1;
    private static final int LENGTH_TO_COMPARE = 5;
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Illegal offset: -1";

    @Test
    void testPeekWithInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(SOURCE_BYTES, INVALID_OFFSET, LENGTH_TO_COMPARE));

        assertEquals(EXPECTED_EXCEPTION_MESSAGE, thrown.getMessage());
    }
}
