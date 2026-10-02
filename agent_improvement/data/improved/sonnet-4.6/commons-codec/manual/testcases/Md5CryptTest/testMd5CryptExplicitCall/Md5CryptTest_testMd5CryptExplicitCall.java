package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptExplicitCall {

    // MD5 crypt output format: $1$<salt up to 8 chars>$<hash>
    private static final String MD5_CRYPT_OUTPUT_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testMd5CryptExplicitCall() {
        // md5Crypt(byte[]) — auto-generates a salt internally
        String hashWithAutoSalt = Md5Crypt.md5Crypt("secret".getBytes());
        assertTrue(hashWithAutoSalt.matches(MD5_CRYPT_OUTPUT_PATTERN));

        // md5Crypt(byte[], String) with null salt — also auto-generates a salt
        String hashWithNullSalt = Md5Crypt.md5Crypt("secret".getBytes(), (String) null);
        assertTrue(hashWithNullSalt.matches(MD5_CRYPT_OUTPUT_PATTERN));
    }
}
