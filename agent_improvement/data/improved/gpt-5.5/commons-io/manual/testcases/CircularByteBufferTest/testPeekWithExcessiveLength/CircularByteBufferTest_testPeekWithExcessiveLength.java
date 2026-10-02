package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithExcessiveLength {

    @Test
    void testPeekWithExcessiveLength() {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] expectedBytes = { 1, 3, 5, 7, 9 };
        final int sourceOffset = 0;
        final int requestedLength = 6;

        assertFalse(buffer.peek(expectedBytes, sourceOffset, requestedLength));
    }
}
