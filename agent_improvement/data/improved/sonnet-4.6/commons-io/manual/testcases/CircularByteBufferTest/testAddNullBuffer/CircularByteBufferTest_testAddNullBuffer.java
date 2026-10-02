package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CircularByteBufferTest_testAddNullBuffer {

    @Test
    @DisplayName("add() with a null byte array should throw NullPointerException")
    void testAddNullBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        // Passing null as the source array must be rejected immediately
        assertThrows(NullPointerException.class, () -> buffer.add(null, 0, 3));
    }
}
