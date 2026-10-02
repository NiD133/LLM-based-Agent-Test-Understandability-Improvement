package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitOffsetUnder {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Reading with a negative offset should throw IndexOutOfBoundsException.
     * Passing offset=-1 to read(byte[], int, int) is below the valid range [0, dest.length],
     * so the stream must reject it before any bytes are transferred.
     */
    @Test
    void testInvalidReadArrayExplicitOffsetUnder() {
        // Use an empty destination array; the exception must be thrown regardless of its length
        final byte[] destination = IOUtils.EMPTY_BYTE_ARRAY;

        // Stream backed by three bytes — content is irrelevant because the invalid offset
        // must be caught before the stream is accessed
        @SuppressWarnings("resource") // stream is not closeable in a way that requires try-with-resources here
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        // offset=-1 is below the valid lower bound of 0, so IndexOutOfBoundsException is expected
        assertThrows(IndexOutOfBoundsException.class, () -> stream.read(destination, -1, 1));
    }
}
