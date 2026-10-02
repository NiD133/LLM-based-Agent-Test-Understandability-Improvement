package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithValidArguments {

    @Test
    void testPeekWithValidArguments() {
        final CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        final byte[] bytesToCompare = { 5, 10, 15, 20, 25 };

        assertFalse(emptyBuffer.peek(bytesToCompare, 0, 5));
    }
}
