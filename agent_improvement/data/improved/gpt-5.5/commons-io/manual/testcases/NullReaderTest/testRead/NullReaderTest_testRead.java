package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class NullReaderTest_testRead {

    private static final int READER_SIZE = 5;
    private static final String READ_AFTER_EOF_MESSAGE = "Read after end of file";

    private static final class TestNullReader extends NullReader {

        TestNullReader(final int size) {
            super(size);
        }

        @Override
        protected int processChar() {
            return (int) getPosition() - 1;
        }
    }

    @Test
    void testRead() throws Exception {
        final TestNullReader reader = new TestNullReader(READER_SIZE);

        assertSequentialReads(reader);
        assertEquals(-1, reader.read(), "End of File");
        assertReadAfterEndOfFileThrows(reader);

        reader.close();
        assertEquals(0, reader.getPosition(), "Available after close");
    }

    private void assertSequentialReads(final TestNullReader reader) throws IOException {
        for (int i = 0; i < READER_SIZE; i++) {
            assertEquals(i, reader.read(), "Check Value [" + i + "]");
        }
    }

    private void assertReadAfterEndOfFileThrows(final TestNullReader reader) {
        try {
            final int result = reader.read();
            fail("Should have thrown an IOException, value=[" + result + "]");
        } catch (final IOException e) {
            assertEquals(READ_AFTER_EOF_MESSAGE, e.getMessage());
        }
    }
}
