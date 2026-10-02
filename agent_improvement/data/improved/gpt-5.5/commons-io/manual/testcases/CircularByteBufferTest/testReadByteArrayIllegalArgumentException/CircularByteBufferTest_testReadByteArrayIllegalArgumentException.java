package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testReadByteArrayIllegalArgumentException {

    @Test
    void testReadByteArrayIllegalArgumentException() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] targetBuffer = new byte[10];

        assertThrows(
                IllegalArgumentException.class,
                () -> buffer.read(targetBuffer, -1, 10),
                "Reading into a negative target offset should be rejected.");

        assertThrows(
                IllegalArgumentException.class,
                () -> buffer.read(targetBuffer, 0, targetBuffer.length + 1),
                "Reading more bytes than the target buffer can hold should be rejected.");
    }
}
