package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing.
@Timeout(3)
public class Md5CryptTest_testCtorDeprecated {

    @Test
    @SuppressWarnings("deprecation")
    void testCtorDeprecated() {
        assertNotNull(new Md5Crypt());
    }
}
