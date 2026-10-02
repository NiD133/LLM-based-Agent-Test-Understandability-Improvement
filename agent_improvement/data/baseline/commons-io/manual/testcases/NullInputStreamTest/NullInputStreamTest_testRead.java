package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

public class NullInputStreamTest_testRead {

    /**
     * Use the same message as in java.io.InputStream.reset() in OpenJDK 8.0.275-1.
     */
    private static final String MARK_RESET_NOT_SUPPORTED = "mark/reset not supported";

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
    void testRead() throws Exception {
        final int size = 5;
        try (InputStream input = new TestNullInputStream(size)) {
            for (int i = 0; i < size; i++) {
                assertEquals(size - i, input.available(), "Check Size [" + i + "]");
                assertEquals(i, input.read(), "Check Value [" + i + "]");
            }
            assertEquals(0, input.available(), "Available after contents all read");
            // Check available is zero after End of file
            assertEquals(-1, input.read(), "End of File");
            assertEquals(0, input.available(), "Available after End of File");
            // Test reading after the end of file
            assertEquals(-1, input.read(), "End of File");
            // Close
            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }
}
