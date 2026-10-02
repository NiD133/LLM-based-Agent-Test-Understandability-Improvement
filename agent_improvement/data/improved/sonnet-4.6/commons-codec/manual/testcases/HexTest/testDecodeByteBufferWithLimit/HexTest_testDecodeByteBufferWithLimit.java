package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferWithLimit {

    /**
     * Allocates a heap ByteBuffer of the given capacity.
     * Subclasses may override to use direct buffers instead.
     */
    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Encodes a string as UTF-8 bytes and wraps them in a ready-to-read ByteBuffer.
     */
    private ByteBuffer getByteBufferUtf8(final String string) {
        final byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer bb = allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        return bb;
    }

    /**
     * Verifies that {@link Hex#decode(ByteBuffer)} respects the buffer's position and limit,
     * decoding only the bytes in the active window [{@code position}, {@code limit}).
     *
     * <p>The full buffer holds the lowercase hex representation of the 16 bytes 0x00–0x0F.
     * Each loop iteration sets a 4-character (2-decoded-byte) sliding window and checks that:
     * <ul>
     *   <li>exactly the two expected bytes are returned, and</li>
     *   <li>no bytes remain in the buffer after decoding (i.e. the window was fully consumed).</li>
     * </ul>
     */
    @Test
    void testDecodeByteBufferWithLimit() throws DecoderException {
        // Each byte 0x00–0x0F is encoded as two hex characters, yielding a 32-character string.
        final String hexEncoded = "000102030405060708090a0b0c0d0e0f";
        final ByteBuffer bb = getByteBufferUtf8(hexEncoded);

        // The 16 decoded byte values that the hex string represents.
        final byte[] expectedDecodedBytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

        // Two hex characters encode one byte, so a window of 4 hex chars decodes to 2 bytes.
        final int HEX_CHARS_PER_BYTE = 2;
        final int WINDOW_SIZE_IN_HEX_CHARS = 4; // covers 2 decoded bytes per iteration

        // There are 15 overlapping 2-byte windows across the 16-byte sequence (indices 0..14).
        final int NUM_SLIDING_WINDOWS = 15;

        for (int i = 0; i < NUM_SLIDING_WINDOWS; i++) {
            // Slide the buffer window by one byte (two hex chars) on each iteration.
            final int windowStart = i * HEX_CHARS_PER_BYTE;
            final int windowEnd   = windowStart + WINDOW_SIZE_IN_HEX_CHARS;
            bb.position(windowStart);
            bb.limit(windowEnd);

            // Decode only the bytes in [position, limit) and verify the result.
            final byte[] decodedWindow = new Hex().decode(bb);
            final byte[] expectedWindow = Arrays.copyOfRange(expectedDecodedBytes, i, i + 2);

            assertEquals(new String(expectedWindow), new String(decodedWindow));
            // After decoding, the buffer should be fully consumed.
            assertEquals(0, bb.remaining());
        }
    }
}
