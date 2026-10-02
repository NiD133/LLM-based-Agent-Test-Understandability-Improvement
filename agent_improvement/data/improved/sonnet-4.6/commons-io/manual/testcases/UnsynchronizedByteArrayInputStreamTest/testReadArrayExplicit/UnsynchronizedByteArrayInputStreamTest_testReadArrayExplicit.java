package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testReadArrayExplicit {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer, final int offset, final int length) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).setOffset(offset).setLength(length).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Reading from an empty stream into a full 10-byte buffer must return END_OF_STREAM
     * and leave the destination buffer unchanged.
     */
    @Test
    void testRead_emptyStream_fullBuffer_returnsEndOfStream() {
        byte[] destBuffer = new byte[10];
        UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);

        int bytesRead = emptyStream.read(destBuffer, 0, 10);

        assertEquals(END_OF_STREAM, bytesRead);
        assertArrayEquals(new byte[10], destBuffer);
    }

    /**
     * Reading from an empty stream into a middle slice of a buffer must return END_OF_STREAM
     * and leave the destination buffer unchanged.
     */
    @Test
    void testRead_emptyStream_bufferSliceInMiddle_returnsEndOfStream() {
        byte[] destBuffer = new byte[10];
        UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);

        int bytesRead = emptyStream.read(destBuffer, 4, 2);

        assertEquals(END_OF_STREAM, bytesRead);
        assertArrayEquals(new byte[10], destBuffer);
    }

    /**
     * Reading from an empty stream into the tail portion of a buffer (offset 4, length 6)
     * must return END_OF_STREAM and leave the destination buffer unchanged.
     */
    @Test
    void testRead_emptyStream_bufferSliceAtEnd_returnsEndOfStream() {
        byte[] destBuffer = new byte[10];
        UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);

        int bytesRead = emptyStream.read(destBuffer, 4, 6);

        assertEquals(END_OF_STREAM, bytesRead);
        assertArrayEquals(new byte[10], destBuffer);
    }

    /**
     * Requesting zero bytes from a non-empty stream must return 0 without consuming any data.
     */
    @Test
    void testRead_nonEmptyStream_zeroLengthRequest_returnsZero() {
        UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        int bytesRead = stream.read(IOUtils.EMPTY_BYTE_ARRAY, 0, 0);

        assertEquals(0, bytesRead);
    }

    /**
     * Partial reads on the same stream instance must advance the position correctly.
     * First read returns 2 bytes; a subsequent read returns the 1 remaining byte.
     */
    @Test
    void testRead_nonEmptyStream_partialThenRemainder_advancesPositionCorrectly() {
        byte[] destBuffer = new byte[10];
        UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });

        // Read first two bytes; only positions 0 and 1 should be populated.
        int bytesRead = stream.read(destBuffer, 0, 2);
        assertEquals(2, bytesRead);
        assertEquals(0xa, destBuffer[0]);
        assertEquals(0xb, destBuffer[1]);
        assertEquals(0, destBuffer[2]);

        // Read up to 10 bytes; only the single remaining byte (0xc) should be returned.
        bytesRead = stream.read(destBuffer, 0, 10);
        assertEquals(1, bytesRead);
        assertEquals(0xc, destBuffer[0]);
    }
}
