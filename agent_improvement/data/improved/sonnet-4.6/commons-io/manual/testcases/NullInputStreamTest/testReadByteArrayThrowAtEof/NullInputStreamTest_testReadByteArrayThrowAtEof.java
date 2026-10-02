package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.EOFException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * Tests that reading a byte array from a NullInputStream configured to throw EOFException
 * works correctly: partial reads near EOF, EOFException thrown at EOF, and re-initialization.
 */
public class NullInputStreamTest_testReadByteArrayThrowAtEof {

    // Stream of 15 bytes; buffer of 10 → first read fills buffer, second read returns
    // remaining 5, third read hits EOF and throws EOFException (throwEofException=true).
    private static final int STREAM_SIZE = 15;
    private static final int BUFFER_SIZE = 10;

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

    @Test
    void testReadByteArrayThrowAtEof() throws Exception {
        final byte[] bytes = new byte[BUFFER_SIZE];

        // markSupported=true, throwEofException=true: reads past EOF must throw EOFException
        try (NullInputStream input = new TestNullInputStream(STREAM_SIZE, true, true)) {

            // First read: fills the entire buffer (10 bytes from a 15-byte stream)
            final int firstReadCount = input.read(bytes);
            assertEquals(bytes.length, firstReadCount, "Read 1");
            for (int i = 0; i < firstReadCount; i++) {
                assertEquals(i, bytes[i], "Check Bytes 1");
            }

            // Second read: returns only the 5 bytes remaining before EOF
            final int secondReadCount = input.read(bytes);
            assertEquals(5, secondReadCount, "Read 2");
            for (int i = 0; i < secondReadCount; i++) {
                assertEquals(firstReadCount + i, bytes[i], "Check Bytes 2");
            }

            // Third read: stream is at EOF → must throw EOFException with a non-blank message
            final IOException eofOnFirstAttempt = assertThrows(EOFException.class, () -> input.read(bytes));
            assertTrue(StringUtils.isNotBlank(eofOnFirstAttempt.getMessage()));

            // Fourth read: stream remains at EOF → must still throw EOFException
            final IOException eofOnSecondAttempt = assertThrows(EOFException.class, () -> input.read(bytes));
            assertTrue(StringUtils.isNotBlank(eofOnSecondAttempt.getMessage()));

            // Re-initialize the stream to position 0 without closing it
            input.init();

            // Fifth read: read with explicit offset and length into the re-initialized stream
            final int offset = 2;
            final int len = 4;
            final int offsetReadCount = input.read(bytes, offset, len);
            assertEquals(len, offsetReadCount, "Read 5");
            for (int i = offset; i < len; i++) {
                assertEquals(i, bytes[i], "Check Bytes 5");
            }
        }
    }
}
