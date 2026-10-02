package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link NullInputStream#read()}, verifying the byte values returned, the
 * reported {@code available()} count, and the end-of-file / post-close behaviour.
 */
public class NullInputStreamTest_testRead {

    /**
     * A {@link NullInputStream} that returns meaningful byte values instead of zeros,
     * so the test can assert on the actual data read.
     * <p>
     * {@link #read()} returns the zero-based index of the byte just read: the first
     * call returns {@code 0}, the second {@code 1}, and so on. This works because
     * {@code read()} increments the position before invoking {@code processByte()}.
     * </p>
     */
    private static final class IndexValueInputStream extends NullInputStream {

        IndexValueInputStream(final int size) {
            super(size);
        }

        @Override
        protected int processByte() {
            return (int) getPosition() - 1;
        }
    }

    @Test
    void testRead() throws Exception {
        final int size = 5;
        try (InputStream input = new IndexValueInputStream(size)) {

            // Read every byte: each call returns its index, and available() counts down.
            for (int i = 0; i < size; i++) {
                final int expectedRemaining = size - i;
                assertEquals(expectedRemaining, input.available(), "Remaining bytes before read [" + i + "]");
                assertEquals(i, input.read(), "Byte value [" + i + "]");
            }
            assertEquals(0, input.available(), "Available after all contents read");

            // At end of file, read() returns -1 and available() stays at 0.
            assertEquals(-1, input.read(), "First read at end of file");
            assertEquals(0, input.available(), "Available at end of file");

            // Reading again past the end of file still returns -1.
            assertEquals(-1, input.read(), "Second read past end of file");

            // After closing, available() reports 0.
            input.close();
            assertEquals(0, input.available(), "Available after close");
        }
    }
}
