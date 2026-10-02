package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithNegativeLength {

    // Arbitrary byte array used as the sourceBuffer argument to peek(); its contents are irrelevant
    // because the method must reject a negative length before inspecting the buffer data.
    private static final byte[] DUMMY_SOURCE_BUFFER = { 1, 4, 3 };
    private static final int OFFSET_ZERO = 0;
    private static final int NEGATIVE_LENGTH = -1;
    private static final String EXPECTED_MESSAGE = "Illegal length: " + NEGATIVE_LENGTH;

    /**
     * Verifies that peek() throws IllegalArgumentException with a descriptive message
     * when the caller supplies a negative length value.
     */
    @Test
    void testPeekWithNegativeLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> buffer.peek(DUMMY_SOURCE_BUFFER, OFFSET_ZERO, NEGATIVE_LENGTH));

        assertEquals(EXPECTED_MESSAGE, exception.getMessage());
    }
}
