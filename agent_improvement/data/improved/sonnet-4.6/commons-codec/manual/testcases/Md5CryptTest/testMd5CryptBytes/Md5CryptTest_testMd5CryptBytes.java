package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptBytes {

    @Test
    void testEmptyByteArrayHashesTheSameAsEmptyString() {
        String salt = "$1$foo";
        String expected = "$1$foo$9mS5ExwgIECGE5YKlD5o91";
        assertEquals(expected, Crypt.crypt(new byte[0], salt));
    }

    @Test
    void testUtf8BytesHashedByDefault() {
        // Java strings with non-ASCII chars are passed as UTF-8 bytes by default.
        // ä ("a with dieresis") is encoded as two bytes: 0xc3 0xa4 in UTF-8.
        String salt = "$1$./$";
        String expected = "$1$./$52agTEQZs877L9jyJnCNZ1";
        assertEquals(expected, Crypt.crypt("täst", salt));
    }

    @Test
    void testIso88591BytesProduceDifferentHashThanUtf8() {
        // ISO-8859-1 encodes ä ("a with dieresis") as a single byte 0xe4,
        // which differs from the two-byte UTF-8 encoding, so the hash differs.
        String salt = "$1$./$";
        String expectedIso88591Hash = "$1$./$J2UbKzGe0Cpe63WZAt6p//";
        byte[] iso88591Bytes = "täst".getBytes(StandardCharsets.ISO_8859_1);
        assertEquals(expectedIso88591Hash, Crypt.crypt(iso88591Bytes, salt));
    }
}
