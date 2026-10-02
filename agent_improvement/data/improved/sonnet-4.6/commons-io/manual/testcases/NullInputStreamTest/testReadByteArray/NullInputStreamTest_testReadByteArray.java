package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testReadByteArray {

    /**
     * A concrete NullInputStream whose processByte/processBytes fill data with
     * position-derived values, allowing assertions to verify the exact bytes read.
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
     * Tests reading from a NullInputStream into a byte array using both
     * read(byte[]) and read(byte[], offset, length) variants, covering
     * partial reads at end-of-stream, repeated EOF behaviour, and re-init.
     *
     * Stream size: 15 bytes. Read buffer: 10 bytes.
     *
     * Expected sequence:
     *  Read 1 – fills all 10 bytes (bytes 0-9 of stream)
     *  Read 2 – fills only 5 bytes (bytes 10-14, stream exhausted)
     *  Read 3 – returns -1 (EOF, no throwEofException)
     *  Read 4 – returns -1 again (repeated EOF is stable)
     *  Re-init – resets stream position to 0
     *  Read 5 – reads 4 bytes starting at array offset 2
     */
    @Test
    void testReadByteArray() throws Exception {
        final byte[] buffer = new byte[10];
        final int streamSize = 15;

        try (NullInputStream input = new TestNullInputStream(streamSize)) {

            // Read 1: full buffer read; stream has 15 bytes, buffer holds 10
            final int bytesRead1 = input.read(buffer);
            assertEquals(buffer.length, bytesRead1, "Read 1: should fill entire buffer");
            for (int i = 0; i < bytesRead1; i++) {
                assertEquals(i, buffer[i], "Read 1: byte at index " + i + " should equal its stream position");
            }

            // Read 2: partial read; only 5 bytes remain in the stream
            final int bytesRead2 = input.read(buffer);
            assertEquals(5, bytesRead2, "Read 2: should read the remaining 5 bytes");
            for (int i = 0; i < bytesRead2; i++) {
                assertEquals(bytesRead1 + i, buffer[i], "Read 2: byte at index " + i + " should equal stream position " + (bytesRead1 + i));
            }

            // Read 3: stream is exhausted, expect EOF (-1)
            final int bytesRead3 = input.read(buffer);
            assertEquals(-1, bytesRead3, "Read 3: should return -1 at EOF");

            // Read 4: repeated read past EOF should also return -1
            final int bytesRead4 = input.read(buffer);
            assertEquals(-1, bytesRead4, "Read 4: repeated EOF read should still return -1");

            // Re-init resets the stream position back to 0 for reuse
            input.init();

            // Read 5: read with explicit offset and length into the buffer
            final int offset = 2;
            final int len = 4;
            final int bytesRead5 = input.read(buffer, offset, len);
            assertEquals(len, bytesRead5, "Read 5: should read exactly " + len + " bytes");
            for (int i = offset; i < len; i++) {
                assertEquals(i, buffer[i], "Read 5: buffer[" + i + "] should equal " + i);
            }
        }
    }
}
