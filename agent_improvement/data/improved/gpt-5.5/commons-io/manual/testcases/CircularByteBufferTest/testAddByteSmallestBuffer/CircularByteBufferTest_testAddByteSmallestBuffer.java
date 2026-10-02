package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddByteSmallestBuffer {

    @Test
    void testAddByteSmallestBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer(1);

        buffer.add((byte) 1);
        assertEquals(1, buffer.read());

        buffer.add((byte) 2);
        assertEquals(2, buffer.read());
    }
}
