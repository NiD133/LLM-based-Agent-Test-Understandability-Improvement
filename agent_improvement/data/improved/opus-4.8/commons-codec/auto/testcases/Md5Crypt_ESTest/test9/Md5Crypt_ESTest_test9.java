package org.apache.commons.codec.digest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

/**
 * Verifies {@link Md5Crypt#md5Crypt(byte[])} when no salt is supplied.
 *
 * <p>Normally this overload draws a random salt from {@link java.security.SecureRandom},
 * which would make the output unpredictable. Under EvoRunner the JVM's non-determinism
 * is mocked, so the random salt is fixed to {@code "........"} and the resulting
 * "$1$" MD5 crypt hash is fully reproducible.</p>
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Md5Crypt_ESTest_test9 extends Md5Crypt_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void md5CryptWithoutSaltUsesMockedRandomSalt() throws Throwable {
        final byte[] emptyKey = new byte[4];

        final String hash = Md5Crypt.md5Crypt(emptyKey);

        assertEquals("$1$........$EixpgL5t2L5AS7g5LlQUj.", hash);
    }
}
