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
public class Md5CryptTest_testZeroOutInput {

    @Test
    void testZeroOutInput() {
        final byte[] buffer = new byte[200];
        Arrays.fill(buffer, (byte) 'A');
        Md5Crypt.md5Crypt(buffer);
        // input password is 0-filled on return
        assertArrayEquals(new byte[buffer.length], buffer);
    }
}
