package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link NullInputStream#available()} reports the full emulated size
 * immediately after the stream is opened, before any bytes have been read.
 */
public class NullInputStreamTest_testAvailableAfterOpen {

    /**
     * A minimal {@link NullInputStream} used purely to exercise {@code available()}.
     * <p>
     * The byte-generating hooks are overridden only to mirror the production test
     * fixture; this test never reads any bytes, so they are not invoked here.
     * </p>
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

    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testAvailableAfterOpen(final int emulatedSize) throws Exception {
        // mark and EOF behaviour are irrelevant to available(), so both are disabled.
        try (InputStream in = new TestNullInputStream(emulatedSize, false, false)) {
            // Nothing has been read yet, so every emulated byte is still available.
            assertEquals(emulatedSize, in.available());
        }
    }
}
