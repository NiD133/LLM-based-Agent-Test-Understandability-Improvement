package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class NullInputStreamTest_testReadByteArrayThrowAtEof {

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
    void testReadByteArrayThrowAtEof() throws Exception {
        final byte[] bytes = new byte[10];
        try (NullInputStream input = new TestNullInputStream(15, true, true)) {
            final int firstReadCount = input.read(bytes);
            assertEquals(bytes.length, firstReadCount, "Read 1");
            assertSequentialBytes(bytes, 0, firstReadCount, 0, "Check Bytes 1");

            final int secondReadCount = input.read(bytes);
            assertEquals(5, secondReadCount, "Read 2");
            assertSequentialBytes(bytes, 0, secondReadCount, firstReadCount, "Check Bytes 2");

            assertEofExceptionHasMessage(() -> input.read(bytes));
            assertEofExceptionHasMessage(() -> input.read(bytes));

            input.init();

            final int offset = 2;
            final int len = 4;
            final int offsetReadCount = input.read(bytes, offset, len);
            assertEquals(len, offsetReadCount, "Read 5");
            assertSequentialBytes(bytes, offset, len, 0, "Check Bytes 2");
        }
    }

    private static void assertEofExceptionHasMessage(final ThrowingIOException operation) {
        final IOException exception = assertThrows(EOFException.class, operation::run);
        assertTrue(StringUtils.isNotBlank(exception.getMessage()));
    }

    private static void assertSequentialBytes(final byte[] bytes, final int startIndex, final int endExclusive, final int expectedOffset,
            final String message) {
        for (int i = startIndex; i < endExclusive; i++) {
            assertEquals(expectedOffset + i, bytes[i], message);
        }
    }

    @FunctionalInterface
    private interface ThrowingIOException {

        void run() throws IOException;
    }
}
