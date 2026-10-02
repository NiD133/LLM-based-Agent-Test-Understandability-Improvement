package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testSkip {

    /**
     * A NullInputStream subclass whose read methods return position-based byte values,
     * making it easy to verify the stream position in assertions.
     *
     * <p>{@code read()} returns {@code (position - 1)} after advancing the position,
     * so reading at stream position N yields byte value N-1 (0-indexed).</p>
     */
    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        TestNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }

        @Override
        protected void processBytes(final byte[] bytes, final int offset, final int length) {
            final int startPos = (int) getPosition() - length;
            for (int i = offset; i < length; i++) {
                bytes[i] = (byte) (startPos + i);
            }
        }
    }

    /**
     * Verifies {@link NullInputStream#skip(long)} with a 10-byte stream that does not
     * throw {@link java.io.EOFException} at end-of-file.
     *
     * <p>Scenario (stream size = 10, positions 0–9):
     * <ol>
     *   <li>Read byte 0  → stream at position 1</li>
     *   <li>Read byte 1  → stream at position 2</li>
     *   <li>Skip 5       → stream at position 7,  returns 5 (all 5 skipped)</li>
     *   <li>Read byte 7  → stream at position 8</li>
     *   <li>Skip 5       → stream at position 10, returns 2 (only 2 bytes remained)</li>
     *   <li>Skip 5 (EOF) → returns -1</li>
     *   <li>Skip 5 (EOF) → returns -1 (EOF is idempotent)</li>
     * </ol>
     * </p>
     */
    @Test
    void testSkip() throws IOException {
        try (TestNullInputStream input = new TestNullInputStream(10, true, false)) {
            // Read the first two bytes to advance the stream to position 2.
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");

            // Skip 5 bytes (positions 2–6); stream moves to position 7.
            assertEquals(5, input.skip(5), "Skip 1");

            // Read byte at position 7; processByte() returns position-1 = 7.
            assertEquals(7, input.read(), "Read 3");

            // Stream is at position 8; only 2 bytes remain before EOF.
            // Requesting a skip of 5 returns only 2 (the actual bytes available).
            assertEquals(2, input.skip(5), "Skip 2");

            // Stream is now at EOF (position 10 == size 10); skip returns -1.
            assertEquals(-1, input.skip(5), "Skip 3 (EOF)");

            // EOF skip is idempotent: subsequent skips also return -1.
            assertEquals(-1, input.skip(5), "Skip 4 (EOF again)");
        }
    }
}
