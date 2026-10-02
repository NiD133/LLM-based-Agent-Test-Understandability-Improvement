package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferWithLimitOddCharacters {

    /**
     * Decoding a hex {@link ByteBuffer} whose readable region (between its
     * position and limit) holds an odd number of characters must fail, since
     * every byte requires exactly two hex characters.
     *
     * <p>Here the buffer exposes a single readable byte (the {@code 'A'} at
     * index 1, with position 1 and limit 2), so decoding must throw a
     * {@link DecoderException}.</p>
     */
    @Test
    void testDecodeByteBufferWithLimitOddCharacters() {
        // Build a 10-byte buffer but expose only one byte for decoding.
        final ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(1, (byte) 'A');
        buffer.position(1);
        buffer.limit(2);

        // One character is an odd hex length, so decoding must be rejected.
        assertThrows(DecoderException.class, () -> new Hex().decode(buffer));
    }
}
