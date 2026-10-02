package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link ReaderInputStream#read(byte[], int, int)} honors the contract for a
 * zero-length read: it must return 0 (never -1) regardless of the stream's state.
 */
public class ReaderInputStreamTest_testReadZero {

    /** Sample text encoded by the stream under test; its length drives the expected read count. */
    private static final String TEST_STRING = "test";

    /**
     * Exercises the zero-length read contract against a single {@link ReaderInputStream} instance.
     *
     * @param inStr       the source text backing {@code inputStream}.
     * @param inputStream the stream under test.
     */
    private void assertReadZeroBehavior(final String inStr, final ReaderInputStream inputStream) throws IOException {
        final byte[] buffer = new byte[30];

        // A zero-length read before consuming any data must report 0 bytes read.
        assertEquals(0, inputStream.read(buffer, 0, 0));

        // Reading the whole content (length + 1 to confirm EOF is reached) returns the actual byte count.
        assertEquals(inStr.length(), inputStream.read(buffer, 0, inStr.length() + 1));

        // A zero-length read after EOF must still report 0 (never -1).
        assertEquals(0, inputStream.read(buffer, 0, 0));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testReadZero() throws Exception {
        // Verify the contract using the deprecated constructor.
        try (ReaderInputStream inputStream = new ReaderInputStream(new StringReader(TEST_STRING))) {
            assertReadZeroBehavior(TEST_STRING, inputStream);
        }

        // Verify the same contract using the recommended builder API.
        try (ReaderInputStream inputStream = ReaderInputStream.builder().setReader(new StringReader(TEST_STRING)).get()) {
            assertReadZeroBehavior(TEST_STRING, inputStream);
        }
    }
}
