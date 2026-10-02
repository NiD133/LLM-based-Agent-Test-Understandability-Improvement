package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testInvalidPrefix {

    @Test
    void testInvalidPrefix() {
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt(new byte[] { 1, 2, 3, 4, 5 }, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!", "(.*a){10000}"));
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt(new byte[] { 1, 2, 3, 4, 5 }, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!", "$(.*a){10000}$"));
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt(new byte[] { 1, 2, 3, 4, 5 }, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "$(.*a){10000}$"));
    }
}
