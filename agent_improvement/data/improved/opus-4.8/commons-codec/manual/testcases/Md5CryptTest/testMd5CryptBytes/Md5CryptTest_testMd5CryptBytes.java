package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that {@link Crypt#crypt(byte[], String)} (the MD5-based "$1$" variant)
 * hashes the raw <em>bytes</em> it is given, so the resulting hash depends on how
 * the plaintext was encoded into bytes rather than on the characters themselves.
 */
// A 3-second cap guards against the occasional hang observed when testing crypt().
@Timeout(3)
public class Md5CryptTest_testMd5CryptBytes {

    @Test
    void testMd5CryptBytes() {
        // An empty byte array must hash to the same value as an empty string.
        assertEquals(
                "$1$foo$9mS5ExwgIECGE5YKlD5o91",
                Crypt.crypt(new byte[0], "$1$foo"));

        // "täst" passed as a String is encoded with the default UTF-8 charset,
        // where 'ä' (U+00E4) becomes the two bytes 0xC3 0xA4.
        assertEquals(
                "$1$./$52agTEQZs877L9jyJnCNZ1",
                Crypt.crypt("täst", "$1$./$"));

        // The same "täst" encoded as ISO-8859-1 represents 'ä' as the single
        // byte 0xE4, so the different byte sequence yields a different hash.
        assertEquals(
                "$1$./$J2UbKzGe0Cpe63WZAt6p//",
                Crypt.crypt("täst".getBytes(StandardCharsets.ISO_8859_1), "$1$./$"));
    }
}
