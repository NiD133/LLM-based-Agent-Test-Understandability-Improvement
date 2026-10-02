package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidConstructor2OffsetUnder {

    private static final int NEGATIVE_OFFSET = -1;

    /**
     * Builds a stream from the given buffer and offset.
     * <p>
     * The builder never performs a byte-array conversion here, so the declared
     * {@link IOException} cannot actually occur; if it ever did, the test fails.
     * </p>
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder()
                    .setByteArray(buffer)
                    .setOffset(offset)
                    .get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testInvalidConstructor2OffsetUnder() {
        // A negative offset is invalid and must be rejected at build time.
        assertThrows(IllegalArgumentException.class,
                () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, NEGATIVE_OFFSET));
    }
}
