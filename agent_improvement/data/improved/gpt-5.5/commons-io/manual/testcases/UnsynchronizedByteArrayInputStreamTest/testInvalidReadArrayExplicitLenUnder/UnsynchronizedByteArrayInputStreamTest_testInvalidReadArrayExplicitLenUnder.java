package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitLenUnder {

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
    @SuppressWarnings("resource")
    void testInvalidReadArrayExplicitLenUnder() {
        final byte[] destination = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream inputStream = newStream(SOURCE_BYTES);

        assertThrows(IndexOutOfBoundsException.class, () -> inputStream.read(destination, 0, -1));
    }
}
