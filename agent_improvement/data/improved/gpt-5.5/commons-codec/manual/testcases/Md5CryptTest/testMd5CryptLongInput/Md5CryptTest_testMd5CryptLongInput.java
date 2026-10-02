package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing.
@Timeout(3)
public class Md5CryptTest_testMd5CryptLongInput {

    private static final String LONG_INPUT = "12345678901234567890";
    private static final String MD5_CRYPT_SALT = "$1$1234";
    private static final String EXPECTED_HASH = "$1$1234$MoxekaNNUgfPRVqoeYjCD/";

    @Test
    void testMd5CryptLongInput() {
        assertEquals(EXPECTED_HASH, Crypt.crypt(LONG_INPUT, MD5_CRYPT_SALT));
    }
}
