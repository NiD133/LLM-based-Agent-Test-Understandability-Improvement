package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies the behaviour of the deprecated public {@link Md5Crypt} constructor.
 */
// A short timeout guards against the occasional hang seen when exercising Md5Crypt.
@Timeout(3)
public class Md5CryptTest_testCtorDeprecated {

    /**
     * The deprecated no-argument constructor should still be usable and must
     * return a non-null instance.
     */
    @Test
    void testCtorDeprecated() {
        final Md5Crypt instance = new Md5Crypt();

        assertNotNull(instance, "The deprecated Md5Crypt constructor should return an instance");
    }
}
