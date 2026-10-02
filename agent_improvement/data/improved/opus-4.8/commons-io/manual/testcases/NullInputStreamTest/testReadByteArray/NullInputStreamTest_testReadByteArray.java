package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link NullInputStream#read(byte[])} and {@link NullInputStream#read(byte[], int, int)}.
 */
public class NullInputStreamTest_testReadByteArray {

    /**
     * A {@link NullInputStream} that fills the supplied array with predictable data:
     * each byte equals its absolute position within the emulated stream (0, 1, 2, ...).
     * This lets the test assert exactly which bytes were produced by each read.
     */
    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
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

    @Test
    void testReadByteArray() throws Exception {
        final int streamSize = 15;
        final byte[] buffer = new byte[10];

        try (NullInputStream input = new TestNullInputStream(streamSize)) {

            // First read fills the whole 10-byte buffer (10 of 15 bytes consumed).
            final int firstReadCount = input.read(buffer);
            assertEquals(buffer.length, firstReadCount, "First read should fill the buffer");
            for (int i = 0; i < firstReadCount; i++) {
                assertEquals(i, buffer[i], "First read should yield bytes 0..9");
            }

            // Second read returns the remaining 5 bytes (positions 10..14).
            final int secondReadCount = input.read(buffer);
            assertEquals(5, secondReadCount, "Second read should return the remaining 5 bytes");
            for (int i = 0; i < secondReadCount; i++) {
                assertEquals(firstReadCount + i, buffer[i], "Second read should continue from byte 10");
            }

            // Stream is now exhausted: further reads report end-of-file with -1.
            assertEquals(-1, input.read(buffer), "Read at end of file should return -1");
            assertEquals(-1, input.read(buffer), "Read past end of file should still return -1");

            // Re-initialize the stream so it can be read again from the start.
            input.init();

            // Read into a sub-range of the buffer using an explicit offset and length.
            final int offset = 2;
            final int length = 4;
            final int offsetReadCount = input.read(buffer, offset, length);
            assertEquals(length, offsetReadCount, "Offset read should return the requested length");
            for (int i = offset; i < length; i++) {
                assertEquals(i, buffer[i], "Offset read should yield bytes at positions 2..3");
            }
        }
    }
}
