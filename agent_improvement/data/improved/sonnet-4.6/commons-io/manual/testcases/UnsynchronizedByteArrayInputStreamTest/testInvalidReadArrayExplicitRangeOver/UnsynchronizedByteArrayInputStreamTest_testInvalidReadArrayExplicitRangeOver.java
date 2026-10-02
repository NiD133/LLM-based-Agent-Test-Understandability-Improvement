package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitRangeOver {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Verifies that read(byte[], off, len) throws IndexOutOfBoundsException when
     * the requested read length exceeds the destination buffer's capacity.
     *
     * The destination buffer has length 0, but the caller requests len=1,
     * so the range [off=0, off+len=1) lies outside the destination array bounds.
     */
    @Test
    void testInvalidReadArrayExplicitRangeOver() {
        // Destination buffer is empty — it cannot accommodate any bytes
        final byte[] emptyDestination = IOUtils.EMPTY_BYTE_ARRAY;

        // The stream has data to read, so the error is purely about the destination range
        @SuppressWarnings("resource") // not necessary to close these resources
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        // Requesting len=1 into a zero-length buffer must throw IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> stream.read(emptyDestination, 0, 1));
    }
}
