package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class NullInputStreamTest_testSkipThrowAtEof {

    private static final int STREAM_SIZE = 10;
    private static final int REQUESTED_SKIP_LENGTH = 5;

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
    void testSkipThrowAtEof() throws Exception {
        try (InputStream input = new PositionReportingNullInputStream(STREAM_SIZE, true, true)) {
            assertEquals(0, input.read(), "Read 1");
            assertEquals(1, input.read(), "Read 2");

            assertEquals(REQUESTED_SKIP_LENGTH, input.skip(REQUESTED_SKIP_LENGTH), "Skip 1");
            assertEquals(7, input.read(), "Read 3");

            assertEquals(2, input.skip(REQUESTED_SKIP_LENGTH), "Skip 2");

            final IOException eofException = assertThrows(EOFException.class, () -> input.skip(REQUESTED_SKIP_LENGTH), "Skip 3 (EOF)");
            assertHasMessage(eofException);

            final IOException postEofException = assertThrows(IOException.class, () -> input.skip(REQUESTED_SKIP_LENGTH),
                    "Expected IOException for skipping after end of file");
            assertHasMessage(postEofException);
        }
    }

    private static void assertHasMessage(final IOException exception) {
        assertTrue(StringUtils.isNotBlank(exception.getMessage()));
    }
}
