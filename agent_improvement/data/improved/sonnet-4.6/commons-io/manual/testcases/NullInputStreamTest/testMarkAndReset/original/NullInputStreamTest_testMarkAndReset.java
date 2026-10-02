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

public class NullInputStreamTest_testMarkAndReset {

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
    void testMarkAndReset() throws Exception {
        int position = 0;
        final int readLimit = 10;
        try (InputStream input = new TestNullInputStream(100, true, false)) {
            assertTrue(input.markSupported(), "Mark Should be Supported");
            // No Mark
            final IOException noMarkException = assertThrows(IOException.class, input::reset);
            assertEquals("No position has been marked", noMarkException.getMessage(), "No Mark IOException message");
            for (; position < 3; position++) {
                assertEquals(position, input.read(), "Read Before Mark [" + position + "]");
            }
            // Mark
            input.mark(readLimit);
            // Read further
            for (int i = 0; i < 3; i++) {
                assertEquals(position + i, input.read(), "Read After Mark [" + i + "]");
            }
            // Reset
            input.reset();
            // Read From marked position
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(position + i, input.read(), "Read After Reset [" + i + "]");
            }
            // Reset after read limit passed
            final IOException resetException = assertThrows(IOException.class, input::reset, "Read limit exceeded, expected IOException");
            assertEquals("Marked position [" + position + "] is no longer valid - passed the read limit [" + readLimit + "]", resetException.getMessage(), "Read limit IOException message");
        }
    }
}
