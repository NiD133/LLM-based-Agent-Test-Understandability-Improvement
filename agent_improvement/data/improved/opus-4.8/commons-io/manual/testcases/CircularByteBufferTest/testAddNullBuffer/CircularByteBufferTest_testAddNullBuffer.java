package org.apache.commons.io.input.buffer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of
 * {@link CircularByteBuffer#add(byte[], int, int)}.
 */
public class CircularByteBufferTest_testAddNullBuffer {

    /**
     * Calling {@code add} with a {@code null} source array must fail fast with a
     * {@link NullPointerException}, because the method rejects a null buffer via
     * {@code Objects.requireNonNull} before reading any bytes.
     */
    @Test
    void testAddNullBuffer() {
        final CircularByteBuffer buffer = new CircularByteBuffer();

        final byte[] nullSource = null;
        final int offset = 0;
        final int length = 3;

        assertThrows(NullPointerException.class,
                () -> buffer.add(nullSource, offset, length));
    }
}
