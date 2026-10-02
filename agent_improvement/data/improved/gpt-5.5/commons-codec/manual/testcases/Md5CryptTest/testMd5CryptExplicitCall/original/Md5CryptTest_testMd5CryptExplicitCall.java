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
public class Md5CryptTest_testMd5CryptExplicitCall {

    @Test
    void testMd5CryptExplicitCall() {
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes()).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches("^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$"));
    }
}
