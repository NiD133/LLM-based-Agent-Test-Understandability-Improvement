package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing.
@Timeout(3)
public class Md5CryptTest_testMd5CryptExplicitCallWithThreadLocalRandom {

    private static final String MD5_CRYPT_HASH_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testMd5CryptExplicitCallWithThreadLocalRandom() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        assertMatchesMd5CryptFormat(Md5Crypt.md5Crypt("secret".getBytes(), threadLocalRandom));
        assertMatchesMd5CryptFormat(Md5Crypt.md5Crypt("secret".getBytes(), (String) null));
    }

    private static void assertMatchesMd5CryptFormat(final String hash) {
        assertTrue(hash.matches(MD5_CRYPT_HASH_PATTERN));
    }
}
