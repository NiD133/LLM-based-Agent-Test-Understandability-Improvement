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
        final int size = 5;
        // throwEofException=true means reading past EOF throws EOFException instead of returning -1
        try (InputStream input = new TestNullInputStream(size, true, true)) {
            // Read all bytes and verify available count decrements correctly
            for (int i = 0; i < size; i++) {
                assertEquals(size - i, input.available(), "Check Size [" + i + "]");
                assertEquals(i, input.read(), "Check Value [" + i + "]");
            }
            assertEquals(0, input.available(), "Available after contents all read");

            // First read past EOF must throw EOFException with a non-blank message
            final IOException firstEofException = assertThrows(EOFException.class, input::read);
            assertTrue(StringUtils.isNotBlank(firstEofException.getMessage()));

            // Subsequent reads past EOF must also throw EOFException with a non-blank message
            final IOException secondEofException = assertThrows(EOFException.class, input::read);
            assertTrue(StringUtils.isNotBlank(secondEofException.getMessage()));

            // After explicit close, available() must return 0
            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }
}
