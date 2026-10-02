package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests that building an {@link UnsynchronizedByteArrayInputStream} with a
 * negative offset is rejected.
 */
public class UnsynchronizedByteArrayInputStreamTest_testInvalidConstructor3OffsetUnder {

    private static final int NEGATIVE_OFFSET = -1;
    private static final int LENGTH = 1;

    /**
     * Builds a stream from the given buffer, offset, and length.
     * <p>
     * The builder's {@code get()} can throw {@link IOException} in general, but
     * not for an in-memory byte array, so any such exception fails the test.
     * </p>
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

    @Test
    void testInvalidConstructor3OffsetUnder() {
        assertThrows(IllegalArgumentException.class,
                () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, NEGATIVE_OFFSET, LENGTH));
    }
}
