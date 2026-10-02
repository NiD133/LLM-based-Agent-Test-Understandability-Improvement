package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testReadByteArrayIllegalArgumentException {

    private static final int TARGET_BUFFER_SIZE = 10;

    @Test
    void testReadThrowsWhenTargetOffsetIsNegative() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] targetBuffer = new byte[TARGET_BUFFER_SIZE];
        final int negativeOffset = -1;
        final int readLength = 10;

        assertThrows(IllegalArgumentException.class,
                () -> buffer.read(targetBuffer, negativeOffset, readLength));
    }

    @Test
    void testReadThrowsWhenLengthExceedsTargetBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] targetBuffer = new byte[TARGET_BUFFER_SIZE];
        final int startOffset = 0;
        final int lengthExceedingBuffer = targetBuffer.length + 1;

        assertThrows(IllegalArgumentException.class,
                () -> buffer.read(targetBuffer, startOffset, lengthExceedingBuffer));
    }
}
