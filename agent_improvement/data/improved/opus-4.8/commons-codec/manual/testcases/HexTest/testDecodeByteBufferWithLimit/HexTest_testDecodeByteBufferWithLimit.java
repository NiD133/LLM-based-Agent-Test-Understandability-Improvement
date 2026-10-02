package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decode(ByteBuffer)} only consumes the bytes that lie
 * between the buffer's current {@code position} and its {@code limit}, and that
 * after decoding the buffer is fully consumed ({@code remaining() == 0}).
 */
public class HexTest_testDecodeByteBufferWithLimit {

    /** Hex text for the 16 bytes 0x00..0x0F, i.e. each byte rendered as two hex characters. */
    private static final String HEX_FOR_BYTES_00_TO_0F = "000102030405060708090a0b0c0d0e0f";

    /** The 16 raw bytes that {@link #HEX_FOR_BYTES_00_TO_0F} decodes to. */
    private static final byte[] DECODED_BYTES = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

    /**
     * Wraps the given string's UTF-8 bytes in a ByteBuffer that is ready for reading
     * (position at 0, limit at the end).
     */
    private ByteBuffer asUtf8ByteBuffer(final String text) {
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    @Test
    void testDecodeByteBufferWithLimit() throws DecoderException {
        final ByteBuffer buffer = asUtf8ByteBuffer(HEX_FOR_BYTES_00_TO_0F);

        // Slide a 4-hex-character window (= 2 decoded bytes) across the buffer.
        // For window index i the window covers hex chars [i*2, i*2 + 4), which is
        // the encoding of the two consecutive bytes DECODED_BYTES[i] and DECODED_BYTES[i + 1].
        for (int i = 0; i < 15; i++) {
            buffer.position(i * 2);
            buffer.limit(i * 2 + 4);

            final String expectedPair = new String(Arrays.copyOfRange(DECODED_BYTES, i, i + 2));
            final String decodedPair = new String(new Hex().decode(buffer));
            assertEquals(expectedPair, decodedPair);

            // decode(ByteBuffer) consumes everything between position and limit.
            assertEquals(0, buffer.remaining());
        }
    }
}
