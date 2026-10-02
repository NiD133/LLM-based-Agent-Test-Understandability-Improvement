package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testInvalidCharacters {

    /**
     * The characters {@code 0} (zero), {@code O} (capital o), {@code I} (capital i) and {@code l}
     * (lower-case L) are deliberately excluded from the Base58 alphabet because they are easy to
     * confuse with one another. Decoding any of them must therefore be rejected.
     */
    @Test
    void testInvalidCharacters() {
        final byte[] charactersNotInBase58Alphabet = "0OIl".getBytes(StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class,
                () -> new Base58().decode(charactersNotInBase58Alphabet));
    }
}
