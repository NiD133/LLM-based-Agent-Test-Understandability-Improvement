package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that reading from a closed NullInputStream throws IOException,
 * regardless of the stream's original size.
 */
public class NullInputStreamTest_testReadAfterClose {

    /**
     * A minimal NullInputStream subclass that provides position-based byte values.
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
     * Confirms that after closing a NullInputStream, available() reflects the
     * initial size before close and a subsequent read() throws IOException.
     */
    @ParameterizedTest
    @MethodSource(AbstractInputStreamTest.ARRAY_LENGTHS_NAME)
    void testReadAfterClose(final int len) throws Exception {
        try (InputStream in = new TestNullInputStream(len, false, false)) {
            // Before closing, available() should equal the stream's total size
            assertEquals(len, in.available());

            in.close();

            // Reading from a closed stream must throw IOException
            assertThrows(IOException.class, in::read);
        }
    }
}
