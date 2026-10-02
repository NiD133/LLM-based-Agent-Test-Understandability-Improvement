package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#decode(Object)} rejects a hex string whose length is odd.
 *
 * <p>A hexadecimal string must contain an even number of characters because every
 * byte is represented by exactly two hex digits. Decoding {@code "6"} (a single,
 * unpaired digit) must therefore fail with a {@link DecoderException}.
 */
public class HexTest_testDecodeHexStringOddCharacters {

    @Test
    void testDecodeHexStringOddCharacters() {
        final Hex hex = new Hex();

        assertThrows(DecoderException.class, () -> hex.decode("6"),
                "Decoding a hex string with an odd number of characters should fail");
    }
}
