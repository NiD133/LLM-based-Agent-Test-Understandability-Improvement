package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Avoid an occasional hang while exercising the MD5 crypt implementation.
@Timeout(3)
public class Md5CryptTest_testMd5CryptBytes {

    @Test
    void testMd5CryptBytes() {
        // Empty byte input hashes the same as an empty string.
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt(new byte[0], "$1$foo"));

        // UTF-8 encodes \u00e4 as two bytes: 0xc3 0xa4.
        assertEquals("$1$./$52agTEQZs877L9jyJnCNZ1", Crypt.crypt("t\u00e4st", "$1$./$"));

        // ISO-8859-1 encodes \u00e4 as one byte: 0xe4.
        assertEquals("$1$./$J2UbKzGe0Cpe63WZAt6p//", Crypt.crypt("t\u00e4st".getBytes(StandardCharsets.ISO_8859_1), "$1$./$"));
    }
}
