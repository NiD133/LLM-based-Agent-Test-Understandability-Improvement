package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnsynchronizedByteArrayInputStream#mark(int)} and
 * {@link UnsynchronizedByteArrayInputStream#reset()}.
 */
public class UnsynchronizedByteArrayInputStreamTest_testMarkReset {

    /** The three bytes backing the stream under test. */
    private static final int FIRST_BYTE = 0xa;
    private static final int SECOND_BYTE = 0xb;
    private static final int THIRD_BYTE = 0xc;

    /**
     * Builds a stream backed by the given buffer. The builder declares a checked
     * {@link IOException}, but it can never be thrown here because the byte array
     * needs no conversion.
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
    void testMarkReset() {
        // The stream is not backed by OS resources, so it need not be closed.
        @SuppressWarnings("resource")
        final UnsynchronizedByteArrayInputStream is =
                newStream(new byte[] { (byte) FIRST_BYTE, (byte) SECOND_BYTE, (byte) THIRD_BYTE });

        // mark/reset is always supported by this stream.
        assertTrue(is.markSupported());

        // Consume the first byte, then place a mark before the second byte.
        assertEquals(FIRST_BYTE, is.read());
        assertTrue(is.markSupported());
        is.mark(10);

        // Read to the end of the stream from the marked position.
        assertEquals(SECOND_BYTE, is.read());
        assertEquals(THIRD_BYTE, is.read());

        // reset() rewinds to the mark, so the same two bytes are read again,
        // followed by end-of-stream.
        is.reset();
        assertEquals(SECOND_BYTE, is.read());
        assertEquals(THIRD_BYTE, is.read());
        assertEquals(END_OF_STREAM, is.read());

        // The mark survives reset(), so rewinding once more replays the bytes.
        is.reset();
        assertEquals(SECOND_BYTE, is.read());
        assertEquals(THIRD_BYTE, is.read());
        assertEquals(END_OF_STREAM, is.read());
    }
}
