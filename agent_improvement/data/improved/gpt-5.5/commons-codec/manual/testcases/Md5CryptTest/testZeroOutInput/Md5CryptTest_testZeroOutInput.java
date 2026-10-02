package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing.
@Timeout(3)
public class Md5CryptTest_testZeroOutInput {

    private static final int PASSWORD_LENGTH = 200;
    private static final byte PASSWORD_BYTE = 'A';

    @Test
    void testZeroOutInput() {
        final byte[] password = new byte[PASSWORD_LENGTH];
        Arrays.fill(password, PASSWORD_BYTE);

        Md5Crypt.md5Crypt(password);

        assertArrayEquals(new byte[password.length], password);
    }
}
