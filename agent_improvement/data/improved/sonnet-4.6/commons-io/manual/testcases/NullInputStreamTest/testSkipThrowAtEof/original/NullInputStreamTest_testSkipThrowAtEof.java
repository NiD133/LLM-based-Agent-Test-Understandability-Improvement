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

public class NullInputStreamTest_testSkipThrowAtEof {

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
    void testSkipThrowAtEof() throws Exception {
        try (InputStream input = new TestNullInputStream(10, true, true)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");
            assertEquals(5, input.skip(5), "Skip 1");
            assertEquals(7, input.read(), "Read 3");
            // only 2 left to skip
            assertEquals(2, input.skip(5), "Skip 2");
            // End of File
            final IOException e1 = assertThrows(EOFException.class, () -> input.skip(5), "Skip 3 (EOF)");
            assertTrue(StringUtils.isNotBlank(e1.getMessage()));
            final IOException e2 = assertThrows(IOException.class, () -> input.skip(5), "Expected IOException for skipping after end of file");
            assertTrue(StringUtils.isNotBlank(e2.getMessage()));
        }
    }
}
