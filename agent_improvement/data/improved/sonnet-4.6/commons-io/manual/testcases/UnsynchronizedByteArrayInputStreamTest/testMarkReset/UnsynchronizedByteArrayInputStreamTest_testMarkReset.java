package org.apache.commons.io.input;

import static org.apache.commons.io.input.UnsynchronizedByteArrayInputStream.END_OF_STREAM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UnsynchronizedByteArrayInputStreamTest_testMarkReset {

    private UnsynchronizedByteArrayInputStream newStream(final byte[] buffer) {
        try {
            return UnsynchronizedByteArrayInputStream.builder().setByteArray(buffer).get();
        } catch (final IOException e) {
            fail("Should never happen because no conversion is needed.", e);
            return null;
        }
    }

    /**
     * Verifies that mark() and reset() allow re-reading from a previously marked position.
     *
     * Scenario:
     *   Stream contains bytes [0x0a, 0x0b, 0x0c].
     *   After reading the first byte, we mark the current position (before 0x0b).
     *   Reading the remaining bytes and then calling reset() must rewind the stream
     *   back to the mark, so 0x0b and 0x0c can be read again — twice in total.
     */
    @Test
    void testMarkReset() {
        final byte first  = (byte) 0x0a;
        final byte second = (byte) 0x0b;
        final byte third  = (byte) 0x0c;

        @SuppressWarnings("resource") // stream wraps an in-memory buffer; closing is not necessary
        final UnsynchronizedByteArrayInputStream stream = newStream(new byte[] { first, second, third });

        // Mark/reset must always be supported for this stream type.
        assertTrue(stream.markSupported());

        // Advance past the first byte, then set the mark at the current position.
        assertEquals(first & 0xff, stream.read());
        assertTrue(stream.markSupported());
        stream.mark(10 /* readAheadLimit — ignored by this implementation */);

        // Read through the remaining bytes to reach end-of-stream.
        assertEquals(second & 0xff, stream.read());
        assertEquals(third  & 0xff, stream.read());

        // First reset: stream rewinds to the mark (before 'second').
        stream.reset();
        assertEquals(second & 0xff, stream.read());
        assertEquals(third  & 0xff, stream.read());
        assertEquals(END_OF_STREAM,  stream.read());

        // Second reset: mark is still valid; re-reading must produce the same bytes.
        stream.reset();
        assertEquals(second & 0xff, stream.read());
        assertEquals(third  & 0xff, stream.read());
        assertEquals(END_OF_STREAM,  stream.read());
    }
}
