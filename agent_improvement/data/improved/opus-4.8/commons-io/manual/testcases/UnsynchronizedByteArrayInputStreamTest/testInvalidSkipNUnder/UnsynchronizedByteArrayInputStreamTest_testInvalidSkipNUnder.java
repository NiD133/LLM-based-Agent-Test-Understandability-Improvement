package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UnsynchronizedByteArrayInputStream#skip(long)} rejects a
 * negative argument, since skipping backward is not supported.
 */
public class UnsynchronizedByteArrayInputStreamTest_testInvalidSkipNUnder {

    /** A negative skip count, which must be rejected by {@code skip}. */
    private static final long NEGATIVE_SKIP = -1;

    /**
     * Builds a stream backed by the given buffer using the public builder API.
     * The declared {@link IOException} never occurs for an in-memory byte array.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testInvalidSkipNUnder() {
        // The backing buffer contents are irrelevant; skip(-1) must fail before any read.
        @SuppressWarnings("resource") // No system resources to release for an in-memory stream.
        final UnsynchronizedByteArrayInputStream stream =
                newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        assertThrows(IllegalArgumentException.class, () -> stream.skip(NEGATIVE_SKIP));
    }
}
