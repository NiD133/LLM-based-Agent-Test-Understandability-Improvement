package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link NullInputStream#available()} reports the remaining bytes while the
 * stream is open, but always returns {@code 0} once the stream has been closed.
 */
public class NullInputStreamTest_testAvailableAfterClose {

    /**
     * A concrete {@link NullInputStream} used purely so the stream can be instantiated;
     * the byte-generation hooks are never exercised by this test because it never reads.
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

    @SuppressWarnings("resource")
    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterClose(final int len) throws Exception {
        final InputStream closedStream;
        try (InputStream openStream = new TestNullInputStream(len, false, false)) {
            // While open, available() reflects the full emulated size.
            assertEquals(len, openStream.available());
            closedStream = openStream;
        }
        // After the try-with-resources closes the stream, no bytes are available.
        assertEquals(0, closedStream.available());
    }
}
