package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddNullBuffer {

    @Test
    void testAddNullBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] nullSourceBuffer = null;

        assertThrows(NullPointerException.class, () -> buffer.add(nullSourceBuffer, 0, 3));
    }
}
