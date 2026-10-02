package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing.
@Timeout(3)
public class Md5CryptTest_testMd5CryptWithEmptySalt {

    @Test
    void testMd5CryptWithEmptySalt() {
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt("secret".getBytes(), ""));
    }
}
