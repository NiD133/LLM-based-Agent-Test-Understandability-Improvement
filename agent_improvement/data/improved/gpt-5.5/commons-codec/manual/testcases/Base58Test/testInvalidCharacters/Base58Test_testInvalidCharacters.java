package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base58Test_testInvalidCharacters {

    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    private static final String CHARACTERS_EXCLUDED_FROM_BASE58_ALPHABET = "0OIl";

    @Test
    void testInvalidCharacters() {
        final byte[] invalidChars = CHARACTERS_EXCLUDED_FROM_BASE58_ALPHABET.getBytes(CHARSET_UTF8);

        assertThrows(IllegalArgumentException.class, () -> new Base58().decode(invalidChars));
    }
}
