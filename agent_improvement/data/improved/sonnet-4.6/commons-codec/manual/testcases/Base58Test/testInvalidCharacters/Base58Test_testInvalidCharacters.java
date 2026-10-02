package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testInvalidCharacters {

    @Test
    void testInvalidCharacters() {
        // Base58 deliberately omits '0', 'O', 'I', and 'l' to avoid visual ambiguity.
        // Passing any of these characters to decode() must throw IllegalArgumentException.
        final byte[] charsExcludedFromBase58Alphabet = "0OIl".getBytes(StandardCharsets.UTF_8);
        assertThrows(IllegalArgumentException.class, () -> new Base58().decode(charsExcludedFromBase58Alphabet));
    }
}
