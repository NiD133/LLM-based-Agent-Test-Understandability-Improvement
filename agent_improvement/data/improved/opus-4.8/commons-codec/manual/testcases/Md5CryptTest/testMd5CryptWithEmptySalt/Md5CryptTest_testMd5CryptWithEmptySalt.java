package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies how {@link Md5Crypt#md5Crypt(byte[], String)} reacts to an empty salt.
 */
// A short timeout guards against the occasional hang seen when exercising this algorithm.
@Timeout(3)
public class Md5CryptTest_testMd5CryptWithEmptySalt {

    /**
     * An empty salt string contains no salt characters, so it cannot match the salt
     * pattern Md5Crypt requires (1 to 8 characters). The call must therefore be
     * rejected with an {@link IllegalArgumentException}.
     */
    @Test
    void testMd5CryptWithEmptySalt() {
        final byte[] password = "secret".getBytes(StandardCharsets.UTF_8);
        final String emptySalt = "";

        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(password, emptySalt));
    }
}
