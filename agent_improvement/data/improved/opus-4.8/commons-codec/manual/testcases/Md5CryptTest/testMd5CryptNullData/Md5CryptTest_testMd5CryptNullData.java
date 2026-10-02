package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that {@link Md5Crypt#md5Crypt(byte[])} rejects {@code null} input.
 */
// A timeout guards against the occasional hang observed when exercising this code.
@Timeout(3)
public class Md5CryptTest_testMd5CryptNullData {

    /**
     * Passing {@code null} as the key bytes must fail fast with a
     * {@link NullPointerException} rather than producing a hash.
     */
    @Test
    void md5CryptThrowsNullPointerExceptionForNullKeyBytes() {
        final byte[] nullKeyBytes = null;

        assertThrows(NullPointerException.class, () -> Md5Crypt.md5Crypt(nullKeyBytes));
    }
}
