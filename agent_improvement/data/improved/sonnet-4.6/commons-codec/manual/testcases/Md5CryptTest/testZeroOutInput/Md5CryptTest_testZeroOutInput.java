package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testZeroOutInput {

    /**
     * Verifies the security contract: md5Crypt() must zero-out the caller's password
     * buffer before returning so that the plaintext credential does not linger in memory.
     */
    @Test
    void testZeroOutInput() {
        // Arrange: fill the password buffer with a known non-zero value
        final int passwordLength = 200;
        final byte[] passwordBuffer = new byte[passwordLength];
        Arrays.fill(passwordBuffer, (byte) 'A');

        // Act: hash the password (the buffer is passed by reference and should be wiped)
        Md5Crypt.md5Crypt(passwordBuffer);

        // Assert: every byte of the original buffer must now be zero
        final byte[] expectedZeroedBuffer = new byte[passwordLength];
        assertArrayEquals(expectedZeroedBuffer, passwordBuffer,
                "md5Crypt() must zero-out the input password buffer after hashing");
    }
}
