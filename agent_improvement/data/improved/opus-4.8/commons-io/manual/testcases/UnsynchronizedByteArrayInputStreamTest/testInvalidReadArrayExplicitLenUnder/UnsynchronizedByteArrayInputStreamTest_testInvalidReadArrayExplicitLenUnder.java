package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UnsynchronizedByteArrayInputStream#read(byte[], int, int)}
 * rejects a negative length (a length that is "under" the valid range) by throwing
 * {@link IndexOutOfBoundsException}.
 */
public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitLenUnder {

    /**
     * Builds a stream backed by the given byte array.
     *
     * @param buffer the backing data.
     * @return a new stream over {@code buffer}.
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
    // Stream resources do not need to be closed for this purely in-memory test.
    @SuppressWarnings("resource")
    void testInvalidReadArrayExplicitLenUnder() {
        final byte[] destination = IOUtils.EMPTY_BYTE_ARRAY;
        final int negativeLength = -1;
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        // A negative length is out of bounds and must be rejected.
        assertThrows(IndexOutOfBoundsException.class, () -> stream.read(destination, 0, negativeLength));
    }
}
