package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidConstructor2OffsetUnder {

    // A negative offset is always invalid; the builder rejects it with IllegalArgumentException.
    private static final int NEGATIVE_OFFSET = -1;

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
    @DisplayName("Builder.setOffset() rejects a negative offset with IllegalArgumentException")
    void testInvalidConstructor2OffsetUnder() {
        assertThrows(IllegalArgumentException.class,
                () -> newStream(IOUtils.EMPTY_BYTE_ARRAY, NEGATIVE_OFFSET));
    }
}
