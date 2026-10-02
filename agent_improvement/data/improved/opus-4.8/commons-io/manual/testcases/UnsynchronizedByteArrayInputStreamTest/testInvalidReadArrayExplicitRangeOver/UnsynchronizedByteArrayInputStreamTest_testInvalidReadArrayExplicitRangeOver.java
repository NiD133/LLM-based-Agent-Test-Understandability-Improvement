package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitRangeOver {

    /**
     * Builds a stream backed by the given bytes, failing the test if the builder
     * unexpectedly raises an I/O error (which it never should for an in-memory array).
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
     * Reading 1 byte into a zero-length destination array is an out-of-bounds request,
     * so {@code read(dest, off, len)} must reject it with an {@link IndexOutOfBoundsException}
     * regardless of how much data the stream actually holds.
     */
    @Test
    void testInvalidReadArrayExplicitRangeOver() {
        final byte[] emptyDestination = IOUtils.EMPTY_BYTE_ARRAY;

        // The stream's own contents are irrelevant here; the range check on the
        // destination array fails before any bytes are copied.
        // These resources do not need to be closed.
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        assertThrows(IndexOutOfBoundsException.class, () -> stream.read(emptyDestination, 0, 1));
    }
}
