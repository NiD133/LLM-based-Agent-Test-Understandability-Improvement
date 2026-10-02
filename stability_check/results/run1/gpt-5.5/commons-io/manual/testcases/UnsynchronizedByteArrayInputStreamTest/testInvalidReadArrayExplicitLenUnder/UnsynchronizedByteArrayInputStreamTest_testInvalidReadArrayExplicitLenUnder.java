package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testInvalidReadArrayExplicitLenUnder {

    private static final byte[] STREAM_CONTENTS = { (byte) 0xa, (byte) 0xb, (byte) 0xc };
    private static final int READ_OFFSET = 0;
    private static final int NEGATIVE_READ_LENGTH = -1;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    @SuppressWarnings("resource") // No need to close a stream backed only by a byte array.
    void testInvalidReadArrayExplicitLenUnder() {
        final byte[] destinationBuffer = IOUtils.EMPTY_BYTE_ARRAY;
        final UnsynchronizedByteArrayInputStream inputStream = newStream(STREAM_CONTENTS);

        assertThrows(IndexOutOfBoundsException.class,
                () -> inputStream.read(destinationBuffer, READ_OFFSET, NEGATIVE_READ_LENGTH));
    }
}
