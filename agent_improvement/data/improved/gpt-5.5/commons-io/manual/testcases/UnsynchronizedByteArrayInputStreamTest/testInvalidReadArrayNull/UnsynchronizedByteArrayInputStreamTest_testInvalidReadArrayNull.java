package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayNull {

    private static final byte[] SOURCE_BYTES = { (byte) 0xa, (byte) 0xb, (byte) 0xc };

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testInvalidReadArrayNull() {
        final byte[] destination = null;

        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream inputStream = newStream(SOURCE_BYTES);

        assertThrows(NullPointerException.class, () -> inputStream.read(destination));
    }
}
