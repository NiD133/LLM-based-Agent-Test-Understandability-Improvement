package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CircularByteBuffer#add(byte[], int, int)} rejects a negative
 * start offset by throwing an {@link IllegalArgumentException}.
 */
public class CircularByteBufferTest_testAddInvalidOffset {

    @Test
    void testAddInvalidOffset() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final byte[] source = { 1, 2, 3 };
        final int negativeOffset = -1;
        final int length = 3;

        // A negative start offset is illegal, so add(...) must reject it.
        assertThrows(IllegalArgumentException.class,
                () -> buffer.add(source, negativeOffset, length));
    }
}
