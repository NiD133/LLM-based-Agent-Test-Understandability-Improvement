package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteArrayOddCharacters {

    /**
     * Hex decoding requires an even number of bytes (each byte pair encodes one hex value).
     * A single-byte input (odd count) must throw DecoderException.
     * The byte 65 is ASCII 'A', representing a single hex digit with no pair.
     */
    @Test
    void testDecodeByteArrayOddCharacters() {
        byte[] singleHexDigit = new byte[] { 65 }; // 65 == 'A', one hex char — invalid (needs a pair)
        assertThrows(DecoderException.class, () -> new Hex().decode(singleHexDigit), "odd number of characters");
    }
}
