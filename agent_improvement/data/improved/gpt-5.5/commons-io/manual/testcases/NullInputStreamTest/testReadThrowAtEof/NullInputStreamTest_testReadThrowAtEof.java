package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class NullInputStreamTest_testReadThrowAtEof {

    private static final int STREAM_SIZE = 5;

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
    void testReadThrowAtEof() throws Exception {
        try (InputStream input = new TestNullInputStream(STREAM_SIZE, true, true)) {
            assertStreamContentsCanBeRead(input);
            assertEquals(0, input.available(), "Available after contents all read");

            assertReadThrowsEofExceptionWithMessage(input);
            assertReadThrowsEofExceptionWithMessage(input);

            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }

    private void assertStreamContentsCanBeRead(final InputStream input) throws IOException {
        for (int index = 0; index < STREAM_SIZE; index++) {
            assertEquals(STREAM_SIZE - index, input.available(), "Check Size [" + index + "]");
            assertEquals(index, input.read(), "Check Value [" + index + "]");
        }
    }

    private void assertReadThrowsEofExceptionWithMessage(final InputStream input) {
        final IOException exception = assertThrows(EOFException.class, input::read);
        assertTrue(StringUtils.isNotBlank(exception.getMessage()));
    }
}
