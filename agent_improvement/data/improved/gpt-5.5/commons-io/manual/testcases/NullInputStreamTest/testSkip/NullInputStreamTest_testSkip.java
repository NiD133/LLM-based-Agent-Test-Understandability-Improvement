package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testSkip {

    private static final int STREAM_SIZE = 10;
    private static final long SKIP_REQUEST = 5;

    private static final class PositionReportingNullInputStream extends NullInputStream {

        PositionReportingNullInputStream(final int size, final boolean markSupported, final boolean throwEofException) {
            super(size, markSupported, throwEofException);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }
    }

    @Test
    void testSkip() throws Exception {
        try (InputStream input = new PositionReportingNullInputStream(STREAM_SIZE, true, false)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");

            assertEquals(5, input.skip(SKIP_REQUEST), "Skip 1");
            assertEquals(7, input.read(), "Read 3");

            assertEquals(2, input.skip(SKIP_REQUEST), "Skip 2");
            assertEquals(-1, input.skip(SKIP_REQUEST), "Skip 3 (EOF)");
            assertEquals(-1, input.skip(SKIP_REQUEST), "Skip 3 (EOF)");
        }
    }
}
