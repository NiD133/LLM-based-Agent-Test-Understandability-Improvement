package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Guard against potential hangs during test execution
@Timeout(3)
public class Md5CryptTest_testCtorDeprecated {

    /**
     * Verifies that the deprecated {@link Md5Crypt} constructor is still accessible
     * and produces a valid (non-null) instance, confirming backward compatibility
     * before it is made private in a future release.
     */
    @Test
    void testCtorDeprecated() {
        assertNotNull(new Md5Crypt());
    }
}
