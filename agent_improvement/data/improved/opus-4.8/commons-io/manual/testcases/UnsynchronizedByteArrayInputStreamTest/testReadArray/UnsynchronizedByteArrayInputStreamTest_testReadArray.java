package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnsynchronizedByteArrayInputStream#read(byte[])}.
 */
public class UnsynchronizedByteArrayInputStreamTest_testReadArray {

    /** Sample three-byte payload reused across the scenarios below. */
    private static final byte[] THREE_BYTES = { (byte) 0xa, (byte) 0xb, (byte) 0xc };

    /**
     * Builds a stream backed by the given byte array, failing the test if the
     * builder unexpectedly reports an I/O error (it never does for a byte array).
     */
    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    @Test
    void testReadArray() {
        // Scenario 1: an empty source returns end-of-stream and leaves the destination untouched.
        final byte[] destFromEmptySource = new byte[10];
        UnsynchronizedByteArrayInputStream stream = newStream(IOUtils.EMPTY_BYTE_ARRAY);
        int bytesRead = stream.read(destFromEmptySource);
        assertEquals(END_OF_STREAM, bytesRead);
        assertArrayEquals(new byte[10], destFromEmptySource);

        // Scenario 2: a zero-length destination always reads zero bytes, even when data is available.
        final byte[] emptyDest = IOUtils.EMPTY_BYTE_ARRAY;
        stream = newStream(THREE_BYTES);
        bytesRead = stream.read(emptyDest);
        assertEquals(0, bytesRead);

        // Scenario 3: a destination larger than the source reads all data and pads nothing else.
        final byte[] largeDest = new byte[10];
        stream = newStream(THREE_BYTES);
        bytesRead = stream.read(largeDest);
        assertEquals(3, bytesRead);
        assertEquals(0xa, largeDest[0]);
        assertEquals(0xb, largeDest[1]);
        assertEquals(0xc, largeDest[2]);
        assertEquals(0, largeDest[3]);

        // Scenario 4: a destination smaller than the source reads in successive chunks.
        final byte[] smallDest = new byte[2];
        stream = newStream(THREE_BYTES);
        bytesRead = stream.read(smallDest);
        assertEquals(2, bytesRead);
        assertEquals(0xa, smallDest[0]);
        assertEquals(0xb, smallDest[1]);
        // The second read returns the single remaining byte.
        bytesRead = stream.read(smallDest);
        assertEquals(1, bytesRead);
        assertEquals(0xc, smallDest[0]);
    }
}
