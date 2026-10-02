package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testPeekWithValidArguments {

    @Test
    void testPeekReturnsFalseWhenBufferIsEmpty() {
        // An empty buffer cannot match any probe sequence — peek must return false.
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        byte[] probeBytes = { 5, 10, 15, 20, 25 };
        int offset = 0;
        int length = probeBytes.length;

        boolean result = emptyBuffer.peek(probeBytes, offset, length);

        assertFalse(result);
    }
}
