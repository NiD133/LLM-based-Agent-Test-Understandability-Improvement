package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferWithLimitOddCharacters {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * Verifies that decoding a ByteBuffer whose remaining content represents
     * an odd number of hex characters throws DecoderException.
     *
     * The buffer is set up so that only one byte ('A', value 65) is visible
     * between position and limit. One hex character is an odd count, which
     * Hex.decode must reject.
     */
    @Test
    void testDecodeByteBufferWithLimitOddCharacters() {
        final ByteBuffer bb = allocate(10);
        bb.put(1, (byte) 65); // place 'A' at index 1
        bb.position(1);
        bb.limit(2);          // exactly one byte remaining — odd hex character count
        assertThrows(DecoderException.class, () -> new Hex().decode(bb));
    }
}
