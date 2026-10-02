package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(3)
public class Md5CryptTest_testMd5CryptExplicitCall {

    private static final String MD5_CRYPT_HASH_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testMd5CryptExplicitCall() {
        assertMd5CryptHash(Md5Crypt.md5Crypt("secret".getBytes()));
        assertMd5CryptHash(Md5Crypt.md5Crypt("secret".getBytes(), (String) null));
    }

    private static void assertMd5CryptHash(final String actualHash) {
        assertTrue(actualHash.matches(MD5_CRYPT_HASH_PATTERN));
    }
}
