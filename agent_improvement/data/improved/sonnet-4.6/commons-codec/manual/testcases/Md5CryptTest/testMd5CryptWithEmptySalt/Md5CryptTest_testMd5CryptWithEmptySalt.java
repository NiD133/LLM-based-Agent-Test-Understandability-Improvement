package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptWithEmptySalt {

    // Md5Crypt requires a salt that matches the "$1$<1-8 chars>" pattern;
    // an empty string contains no prefix or salt characters and must be rejected.
    @Test
    void testMd5CryptWithEmptySalt() {
        byte[] password = "secret".getBytes();
        String emptySalt = "";

        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt(password, emptySalt));
    }
}
