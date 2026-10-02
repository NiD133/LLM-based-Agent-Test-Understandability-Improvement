package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests that peek() returns false when the requested length exceeds the number
 * of bytes actually present in the buffer.
 */
public class CircularByteBufferTest_testPeekWithExcessiveLength {

    @Test
    void testPeekReturnsFalseWhenLengthExceedsBufferContents() {
        // An empty CircularByteBuffer holds no bytes.
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();

        // The source array has 5 bytes, but we ask peek() to match 6 bytes —
        // one more than the source array contains. Since the buffer is empty its
        // stored bytes don't match the source bytes, so peek() must return false.
        byte[] sourceBytes = { 1, 3, 5, 7, 9 };
        int offset = 0;
        int excessiveLength = 6; // larger than sourceBytes.length (5)

        assertFalse(emptyBuffer.peek(sourceBytes, offset, excessiveLength));
    }
}
