package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitOffsetUnder {

    /**
     * Builds a stream backed by the given bytes. Building never actually fails here because the
     * source is already a {@code byte[]} (no conversion is required), so an {@link IOException}
     * would indicate a broken test setup rather than an expected outcome.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Reading into a destination array with a negative offset is out of bounds, so
     * {@code read(byte[], off, len)} must reject it with an {@link IndexOutOfBoundsException}.
     */
    @Test
    void testInvalidReadArrayExplicitOffsetUnder() {
        final byte[] destination = IOUtils.EMPTY_BYTE_ARRAY;
        final int negativeOffset = -1;
        final int length = 1;

        // Not necessary to close this in-memory stream.
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        assertThrows(IndexOutOfBoundsException.class, () -> stream.read(destination, negativeOffset, length));
    }
}
