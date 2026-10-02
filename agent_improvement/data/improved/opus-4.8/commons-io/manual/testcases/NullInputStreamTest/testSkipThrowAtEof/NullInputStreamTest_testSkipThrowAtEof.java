package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * Tests that {@link NullInputStream#skip(long)} throws an {@link EOFException}
 * once the emulated end of file has been reached, when the stream is configured
 * with {@code throwEofException == true}.
 */
public class NullInputStreamTest_testSkipThrowAtEof {

    /**
     * A {@link NullInputStream} whose {@code read()} returns the byte value at the
     * position just read (i.e. {@code position - 1}), so that the test can assert
     * on concrete returned values instead of zeros.
     */
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
    void testSkipThrowAtEof() throws Exception {
        final int streamSize = 10;
        try (InputStream input = new TestNullInputStream(streamSize, true, true)) {
            // Read the first two bytes: positions advance to 1 and 2.
            assertEquals(0, input.read(), "read() at position 0");
            assertEquals(1, input.read(), "read() at position 1");

            // Skip 5 bytes (positions 2 -> 7), then read the byte at position 7.
            assertEquals(5, input.skip(5), "skip(5) from position 2");
            assertEquals(7, input.read(), "read() at position 7");

            // Only 2 bytes remain (positions 8 and 9), so skip(5) skips just 2.
            assertEquals(2, input.skip(5), "skip(5) with only 2 bytes left");

            // Now at end of file: every further skip must throw an EOFException.
            final IOException firstEof =
                assertThrows(EOFException.class, () -> input.skip(5), "skip(5) at EOF");
            assertTrue(StringUtils.isNotBlank(firstEof.getMessage()), "EOFException must carry a message");

            final IOException secondEof =
                assertThrows(IOException.class, () -> input.skip(5), "skip(5) again past EOF");
            assertTrue(StringUtils.isNotBlank(secondEof.getMessage()), "EOFException must carry a message");
        }
    }
}
