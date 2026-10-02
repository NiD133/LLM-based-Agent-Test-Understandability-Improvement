package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptLongInput {

    // MD5 crypt processes passwords in 16-byte blocks; a password longer than
    // 16 characters exercises the loop that folds the extra bytes into the digest.
    private static final String PASSWORD_LONGER_THAN_16_CHARS = "12345678901234567890";
    private static final String SALT = "$1$1234";
    private static final String EXPECTED_HASH = "$1$1234$MoxekaNNUgfPRVqoeYjCD/";

    @Test
    void testMd5CryptLongInput() {
        assertEquals(EXPECTED_HASH, Crypt.crypt(PASSWORD_LONGER_THAN_16_CHARS, SALT));
    }
}
