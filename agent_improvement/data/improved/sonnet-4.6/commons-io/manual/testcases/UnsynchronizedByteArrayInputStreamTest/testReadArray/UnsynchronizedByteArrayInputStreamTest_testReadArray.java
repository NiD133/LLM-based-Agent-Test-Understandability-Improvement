package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testReadArray {

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

    @Test
    void testReadArray() {
        // Scenario 1: reading from an empty stream into a non-empty buffer returns END_OF_STREAM
        // and leaves the destination buffer unchanged (all zeros).
        byte[] dest = new byte[10];
        UnsynchronizedByteArrayInputStream emptyStream = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        int bytesRead = emptyStream.read(dest);
        assertEquals(END_OF_STREAM, bytesRead);
        assertArrayEquals(new byte[10], dest);

        // Scenario 2: reading into a zero-length buffer returns 0 (no bytes consumed)
        // even when the stream has data available.
        byte[] zeroDest = IOUtils.EMPTY_BYTE_ARRAY;
        UnsynchronizedByteArrayInputStream threeByteStream = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        bytesRead = threeByteStream.read(zeroDest);
        assertEquals(0, bytesRead);

        // Scenario 3: reading into a buffer larger than the stream returns exactly
        // as many bytes as the stream contains; remaining buffer positions stay zero.
        byte[] largeDest = new byte[10];
        UnsynchronizedByteArrayInputStream threeByteStream2 = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        bytesRead = threeByteStream2.read(largeDest);
        assertEquals(3, bytesRead);
        assertEquals(0xa, largeDest[0]);
        assertEquals(0xb, largeDest[1]);
        assertEquals(0xc, largeDest[2]);
        assertEquals(0, largeDest[3]); // byte beyond data is untouched

        // Scenario 4: reading into a buffer smaller than the stream fills the buffer
        // and subsequent reads continue from where the previous read left off.
        byte[] smallDest = new byte[2];
        UnsynchronizedByteArrayInputStream threeByteStream3 = newStream(new byte[] { (byte) 0xa, (byte) 0xb, (byte) 0xc });
        // First read fills the 2-byte buffer with the first two stream bytes.
        bytesRead = threeByteStream3.read(smallDest);
        assertEquals(2, bytesRead);
        assertEquals(0xa, smallDest[0]);
        assertEquals(0xb, smallDest[1]);
        // Second read picks up the remaining one byte from the stream.
        bytesRead = threeByteStream3.read(smallDest);
        assertEquals(1, bytesRead);
        assertEquals(0xc, smallDest[0]);
    }
}
