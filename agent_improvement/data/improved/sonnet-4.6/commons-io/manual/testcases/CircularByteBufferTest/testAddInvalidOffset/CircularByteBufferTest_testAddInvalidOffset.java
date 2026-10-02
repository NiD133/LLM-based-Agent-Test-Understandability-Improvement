package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddInvalidOffset {

    // A negative offset is never valid; the add() contract requires offset >= 0.
    private static final int NEGATIVE_OFFSET = -1;

    @Test
    void testAddThrowsIllegalArgumentExceptionWhenOffsetIsNegative() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] data = { 1, 2, 3 };

        assertThrows(IllegalArgumentException.class,
                () -> buffer.add(data, NEGATIVE_OFFSET, data.length));
    }
}
