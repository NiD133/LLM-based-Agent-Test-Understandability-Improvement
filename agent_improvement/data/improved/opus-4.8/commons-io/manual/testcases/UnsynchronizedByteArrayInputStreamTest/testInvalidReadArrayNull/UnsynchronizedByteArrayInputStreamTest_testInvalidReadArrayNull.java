package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayNull {

    /**
     * Builds a stream over the given bytes. The builder declares a checked
     * {@link IOException}, but it can never actually be thrown here because the
     * source is already a {@code byte[]} and needs no conversion.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] data) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(data).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testInvalidReadArrayNull() {
        // Reading into a null destination array must fail fast with a NullPointerException.
        final byte[] nullDestination = null;
        // The stream content is irrelevant; the null check happens before any bytes are read.
        @SuppressWarnings("resource") // No need to close an in-memory stream.
        final UnsynchronizedByteArrayInputStream stream =
                newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        assertThrows(NullPointerException.class, () -> stream.read(nullDestination));
    }
}
