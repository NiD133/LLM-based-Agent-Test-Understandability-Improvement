package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that building an {@link UnsynchronizedByteArrayInputStream} with a negative
 * length is rejected.
 */
public class UnsynchronizedByteArrayInputStreamTest_testInvalidConstructor3LengthUnder {

    /**
     * Builds a stream from the given byte array, offset and length.
     * <p>
     * The builder's {@code get()} declares a checked {@link IOException}, but it can never
     * be thrown here because the origin is already a {@code byte[]} (no conversion happens),
     * so any such exception is reported as an unexpected test failure.
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder()
                    .setByteArray(buffer)
                    .setOffset(offset)
                    .setLength(length)
                    .get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * A negative length must be rejected with an {@link IllegalArgumentException}.
     */
    @Test
    void testInvalidConstructor3LengthUnder() {
        final int negativeLength = -1;
        assertThrows(IllegalArgumentException.class,
                () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, 0, negativeLength));
    }
}
