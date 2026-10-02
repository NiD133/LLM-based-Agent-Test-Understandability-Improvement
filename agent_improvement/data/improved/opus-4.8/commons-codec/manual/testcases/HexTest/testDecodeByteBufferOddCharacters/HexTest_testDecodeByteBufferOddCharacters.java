package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decode(ByteBuffer)} rejects input whose length is an
 * odd number of hex characters.
 *
 * <p>Valid hex always comes in pairs (two characters encode one byte), so a
 * buffer holding a single character cannot be decoded and must raise a
 * {@link DecoderException}.
 */
public class HexTest_testDecodeByteBufferOddCharacters {

    @Test
    void testDecodeByteBufferOddCharacters() {
        // A buffer with a single character ('A') -> odd length, not decodable.
        final ByteBuffer oddLengthBuffer = ByteBuffer.allocate(1);
        oddLengthBuffer.put((byte) 'A');
        oddLengthBuffer.flip();

        assertThrows(DecoderException.class, () -> new Hex().decode(oddLengthBuffer));
    }
}
