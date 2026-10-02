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

public class NullInputStreamTest_testReadByteArrayThrowAtEof {

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
    void testReadByteArrayThrowAtEof() throws Exception {
        final byte[] bytes = new byte[10];
        try (NullInputStream input = new TestNullInputStream(15, true, true)) {
            // Read into array
            final int count1 = input.read(bytes);
            assertEquals(bytes.length, count1, "Read 1");
            for (int i = 0; i < count1; i++) {
                assertEquals(i, bytes[i], "Check Bytes 1");
            }
            // Read into array
            final int count2 = input.read(bytes);
            assertEquals(5, count2, "Read 2");
            for (int i = 0; i < count2; i++) {
                assertEquals(count1 + i, bytes[i], "Check Bytes 2");
            }
            // End of File
            final IOException e1 = assertThrows(EOFException.class, () -> input.read(bytes));
            assertTrue(StringUtils.isNotBlank(e1.getMessage()));
            // Test reading after the end of file
            final IOException e2 = assertThrows(EOFException.class, () -> input.read(bytes));
            assertTrue(StringUtils.isNotBlank(e2.getMessage()));
            // reset by closing
            input.init();
            // Read into array using offset & length
            final int offset = 2;
            final int len = 4;
            final int count5 = input.read(bytes, offset, len);
            assertEquals(len, count5, "Read 5");
            for (int i = offset; i < len; i++) {
                assertEquals(i, bytes[i], "Check Bytes 2");
            }
        }
    }
}
