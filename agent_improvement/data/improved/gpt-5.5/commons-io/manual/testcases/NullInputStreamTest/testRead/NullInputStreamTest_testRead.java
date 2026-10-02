package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testRead {

    private static final int STREAM_SIZE = 5;

    private static final class TestNullInputStream extends NullInputStream {

        TestNullInputStream(final int size) {
            super(size);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }
    }

    @Test
    void testRead() throws Exception {
        final int size = STREAM_SIZE;

        try (InputStream input = new TestNullInputStream(size)) {
            for (int position = 0; position < size; position++) {
                assertEquals(size - position, input.available(), "Check Size [" + position + "]");
                assertEquals(position, input.read(), "Check Value [" + position + "]");
            }

            assertEquals(0, input.available(), "Available after contents all read");
            assertEquals(-1, input.read(), "End of File");
            assertEquals(0, input.available(), "Available after End of File");
            assertEquals(-1, input.read(), "End of File");

            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }
}
