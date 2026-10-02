package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testMarkAndReset {

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
        final int readLimit = 10;
        final int bytesBeforeMark = 3;

        try (InputStream input = new TestNullInputStream(100, true, false)) {
            assertTrue(input.markSupported(), "Mark Should be Supported");

            // reset() before any mark is set should throw IOException
            final IOException noMarkException = assertThrows(IOException.class, input::reset);
            assertEquals("No position has been marked", noMarkException.getMessage(), "No Mark IOException message");

            // Advance the stream by reading bytesBeforeMark bytes before setting the mark
            for (int i = 0; i < bytesBeforeMark; i++) {
                assertEquals(i, input.read(), "Read Before Mark [" + i + "]");
            }

            // Set mark at current stream position (position bytesBeforeMark)
            input.mark(readLimit);

            // Read 3 bytes past the mark to verify reading continues normally after marking
            for (int i = 0; i < 3; i++) {
                assertEquals(bytesBeforeMark + i, input.read(), "Read After Mark [" + i + "]");
            }

            // Reset to the marked position and re-read, this time going past the read limit
            input.reset();
            for (int i = 0; i < readLimit + 1; i++) {
                assertEquals(bytesBeforeMark + i, input.read(), "Read After Reset [" + i + "]");
            }

            // After reading more than readLimit bytes past the mark, reset() must fail
            final IOException resetException = assertThrows(IOException.class, input::reset, "Read limit exceeded, expected IOException");
            assertEquals(
                "Marked position [" + bytesBeforeMark + "] is no longer valid - passed the read limit [" + readLimit + "]",
                resetException.getMessage(),
                "Read limit IOException message");
        }
    }
}
