package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferOddCharacters {

    @Test
    void testDecodeByteBufferOddCharacters() {
        // A single byte 'A' (0x41) in a ByteBuffer represents one hex character.
        // Hex decoding requires pairs of characters (two hex chars per output byte),
        // so a buffer with an odd character count must throw DecoderException.
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) 'A');
        buffer.flip();

        assertThrows(DecoderException.class, () -> new Hex().decode(buffer));
    }
}
