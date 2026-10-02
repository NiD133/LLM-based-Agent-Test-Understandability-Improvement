package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeBadCharacterPos0 {

    /**
     * Verifies that decoding a string whose first character is not a valid
     * hexadecimal digit throws a {@link DecoderException}.
     *
     * <p>'q' at position 0 of "q0" is an illegal hex character (valid hex
     * digits are 0-9, a-f, A-F), so {@link Hex#decode(Object)} must reject
     * it immediately rather than producing silently wrong output.</p>
     */
    @Test
    void testDecodeBadCharacterPos0() {
        assertThrows(DecoderException.class, () -> new Hex().decode("q0"));
    }
}
