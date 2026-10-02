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

    @Test
    void testInvalidReadArrayExplicitOffsetUnder() {
        final byte[] destination = IOUtils.EMPTY_BYTE_ARRAY;
        final byte[] source = { (byte) 0xa, (byte) 0xb, (byte) 0xc };
        final int invalidOffset = -1;
        final int requestedLength = 1;

        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream inputStream = newStream(source);

        assertThrows(IndexOutOfBoundsException.class, () -> inputStream.read(destination, invalidOffset, requestedLength));
    }
}
