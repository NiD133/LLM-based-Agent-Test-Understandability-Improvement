package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testMarkAndReset {

    private static final int STREAM_SIZE = 100;
    private static final int BYTES_READ_BEFORE_MARK = 3;
    private static final int BYTES_READ_AFTER_MARK = 3;
    private static final int READ_LIMIT = 10;

    private static final class TestNullInputStream extends NullInputStream {

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
    void testMarkAndReset() throws Exception {
        try (InputStream input = new TestNullInputStream(STREAM_SIZE, true, false)) {
            assertTrue(input.markSupported(), "Mark Should be Supported");

            assertResetWithoutMarkFails(input);

            readExpectedBytes(input, 0, BYTES_READ_BEFORE_MARK, "Read Before Mark");

            final int markedPosition = BYTES_READ_BEFORE_MARK;
            input.mark(READ_LIMIT);

            readExpectedBytes(input, markedPosition, BYTES_READ_AFTER_MARK, "Read After Mark");

            input.reset();
            readExpectedBytes(input, markedPosition, READ_LIMIT + 1, "Read After Reset");

            assertResetAfterReadLimitFails(input, markedPosition);
        }
    }

    private static void assertResetWithoutMarkFails(final InputStream input) {
        final IOException noMarkException = assertThrows(IOException.class, input::reset);
        assertEquals("No position has been marked", noMarkException.getMessage(), "No Mark IOException message");
    }

    private static void assertResetAfterReadLimitFails(final InputStream input, final int markedPosition) {
        final IOException resetException = assertThrows(IOException.class, input::reset, "Read limit exceeded, expected IOException");
        assertEquals("Marked position [" + markedPosition + "] is no longer valid - passed the read limit [" + READ_LIMIT + "]",
                resetException.getMessage(), "Read limit IOException message");
    }

    private static void readExpectedBytes(final InputStream input, final int firstExpectedByte, final int byteCount,
            final String assertionPrefix) throws IOException {
        for (int offset = 0; offset < byteCount; offset++) {
            assertEquals(firstExpectedByte + offset, input.read(), assertionPrefix + " [" + offset + "]");
        }
    }
}
