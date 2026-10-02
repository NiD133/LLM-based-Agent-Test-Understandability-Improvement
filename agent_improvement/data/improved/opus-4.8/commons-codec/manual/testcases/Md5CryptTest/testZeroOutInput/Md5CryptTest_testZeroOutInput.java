package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that {@link Md5Crypt#md5Crypt(byte[])} clears the caller's
 * plaintext password from memory before returning.
 */
// Bound the test duration to guard against an occasional hang.
@Timeout(3)
public class Md5CryptTest_testZeroOutInput {

    @Test
    void clearsPasswordBufferAfterHashing() {
        // Given a password buffer filled with non-zero bytes.
        final byte[] passwordBuffer = new byte[200];
        Arrays.fill(passwordBuffer, (byte) 'A');

        // When the buffer is hashed.
        Md5Crypt.md5Crypt(passwordBuffer);

        // Then md5Crypt has overwritten every byte with 0 so the plaintext
        // password no longer lingers in memory.
        final byte[] allZeros = new byte[passwordBuffer.length];
        assertArrayEquals(allZeros, passwordBuffer);
    }
}
