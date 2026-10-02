package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CircularByteBuffer#read(byte[], int, int)} rejects illegal
 * arguments by throwing {@link IllegalArgumentException}.
 */
public class CircularByteBufferTest_testReadByteArrayIllegalArgumentException {

    @Test
    void testReadByteArrayIllegalArgumentException() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] target = new byte[10];

        // A negative target offset is illegal.
        assertThrows(IllegalArgumentException.class,
                () -> buffer.read(target, -1, 10));

        // Offset plus length must not exceed the target array's length;
        // here 0 + 11 > 10, so the target array is too small.
        assertThrows(IllegalArgumentException.class,
                () -> buffer.read(target, 0, target.length + 1));
    }
}
