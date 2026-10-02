package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnsynchronizedByteArrayInputStream#read(byte[], int, int)}, the explicit
 * offset/length read overload.
 */
public class UnsynchronizedByteArrayInputStreamTest_testReadArrayExplicit {

    /**
     * Creates a stream backed by the given bytes. The builder declares a checked
     * {@link IOException}, but it can never be thrown here because no conversion is
     * needed for an in-memory byte array.
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
    void testReadArrayExplicit() {
        // An empty stream always reports end-of-stream and never touches the destination buffer,
        // regardless of the requested offset/length window.
        assertEmptyStreamLeavesBufferUntouched(0, 10);
        assertEmptyStreamLeavesBufferUntouched(4, 2);
        assertEmptyStreamLeavesBufferUntouched(4, 6);

        // Requesting zero bytes reads nothing (returns 0) even when data is available.
        final UnsynchronizedByteArrayInputStream zeroLengthRead =
                newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        assertEquals(0, zeroLengthRead.read(IOUtils.EMPTY_BYTE_ARRAY, 0, 0));

        // Partial then remaining reads: a 3-byte stream read in chunks of 2 then up to 10.
        final UnsynchronizedByteArrayInputStream stream =
                newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        final byte[] buffer = new byte[10];

        // First read: ask for 2 bytes, get the first 2 (0xa, 0xb); the rest of the buffer is untouched.
        assertEquals(2, stream.read(buffer, 0, 2));
        assertEquals(0xa, buffer[0]);
        assertEquals(0xb, buffer[1]);
        assertEquals(0, buffer[2]);

        // Second read: ask for up to 10 bytes, but only the final byte (0xc) remains.
        assertEquals(1, stream.read(buffer, 0, 10));
        assertEquals(0xc, buffer[0]);
    }

    /**
     * Reads into a fresh 10-byte buffer from an empty stream and asserts that the read reports
     * end-of-stream while leaving the buffer all-zeros.
     *
     * @param offset the destination offset passed to read.
     * @param length the requested length passed to read.
     */
    private void assertEmptyStreamLeavesBufferUntouched(final int offset, final int length) {
        final byte[] buffer = new byte[10];
        final UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);

        final int read = emptyStream.read(buffer, offset, length);

        assertEquals(END_OF_STREAM, read);
        assertArrayEquals(new byte[10], buffer);
    }
}
