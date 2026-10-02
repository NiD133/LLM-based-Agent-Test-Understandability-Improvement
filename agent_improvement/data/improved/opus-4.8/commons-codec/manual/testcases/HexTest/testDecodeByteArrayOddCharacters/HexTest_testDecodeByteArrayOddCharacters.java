package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hex#decode(byte[])} rejects input whose decoded text has
 * an odd number of hexadecimal characters.
 */
public class HexTest_testDecodeByteArrayOddCharacters {

    @Test
    void testDecodeByteArrayOddCharacters() {
        // The single byte 65 decodes (UTF-8) to the one-character string "A".
        // A single hex character is invalid, since each byte needs two, so
        // decoding must fail with a DecoderException.
        final byte[] singleHexCharacter = { 65 };

        assertThrows(DecoderException.class,
            () -> new Hex().decode(singleHexCharacter),
            "odd number of characters");
    }
}
