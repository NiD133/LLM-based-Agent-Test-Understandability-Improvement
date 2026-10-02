package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UnsynchronizedByteArrayInputStream#read(byte[], int, int)} rejects a
 * negative length argument by throwing an {@link IndexOutOfBoundsException}.
 */
public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitLenUnder {

    /**
     * Builds a stream backed by the given bytes. The builder never actually performs a
     * conversion here, so the declared {@link IOException} cannot occur in practice.
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
    // not necessary to close these resources
    @SuppressWarnings("resource")
    void testInvalidReadArrayExplicitLenUnder() {
        final int negativeLength = -1;
        final byte[] destination = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        assertThrows(IndexOutOfBoundsException.class, () -> stream.read(destination, 0, negativeLength));
    }
}
