package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testRead {

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

            // Read each byte: available() decrements and read() returns the byte value (0-indexed position)
            for (int i = 0; i < size; i++) {
                assertEquals(size - i, input.available(), "Check Size [" + i + "]");
                assertEquals(i, input.read(), "Check Value [" + i + "]");
            }

            // All content consumed: available() should be zero
            assertEquals(0, input.available(), "Available after contents all read");

            // First read past EOF returns -1; available() stays zero
            assertEquals(-1, input.read(), "End of File");
            assertEquals(0, input.available(), "Available after End of File");

            // Subsequent reads past EOF also return -1
            assertEquals(-1, input.read(), "End of File");

            // After explicit close, available() still returns zero
            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }
}
