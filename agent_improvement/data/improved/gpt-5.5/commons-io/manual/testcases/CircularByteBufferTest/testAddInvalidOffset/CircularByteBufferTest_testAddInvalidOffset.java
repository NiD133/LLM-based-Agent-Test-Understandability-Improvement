package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddInvalidOffset {

    private static final byte[] SOURCE_BYTES = { 1, 2, 3 };
    private static final int INVALID_NEGATIVE_OFFSET = -1;
    private static final int BYTES_TO_ADD = 3;

    @Test
    void testAddInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        assertThrows(IllegalArgumentException.class,
                () -> buffer.add(SOURCE_BYTES, INVALID_NEGATIVE_OFFSET, BYTES_TO_ADD));
    }
}
