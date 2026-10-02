package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testReadArrayExplicit {

    private static final int DESTINATION_BUFFER_LENGTH = 10;

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private void assertEndOfStreamLeavesBufferUnchanged(final int destinationOffset, final int requestedLength) {
        final byte[] buffer = new byte[DESTINATION_BUFFER_LENGTH];
        final UnsynchronizedByteArrayInputStream inputStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);

        final int read = inputStream.read(buffer, destinationOffset, requestedLength);

        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[DESTINATION_BUFFER_LENGTH], buffer);
    }

    @Test
    void testReadArrayExplicit() {
        assertEndOfStreamLeavesBufferUnchanged(0, 10);
        assertEndOfStreamLeavesBufferUnchanged(4, 2);
        assertEndOfStreamLeavesBufferUnchanged(4, 6);

        byte[] buffer = IOUtils.EMPTY_BYTE_ARRAY;
        UnsynchronizedByteArrayInputStream inputStream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        int read = inputStream.read(buffer, 0, 0);
        assertEquals(0, read);

        buffer = new byte[DESTINATION_BUFFER_LENGTH];
        inputStream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        read = inputStream.read(buffer, 0, 2);
        assertEquals(2, read);
        assertEquals(0xa, buffer[0]);
        assertEquals(0xb, buffer[1]);
        assertEquals(0, buffer[2]);

        read = inputStream.read(buffer, 0, 10);
        assertEquals(1, read);
        assertEquals(0xc, buffer[0]);
    }
}
