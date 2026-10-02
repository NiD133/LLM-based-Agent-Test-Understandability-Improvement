package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Random;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockRandom;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Md5Crypt_ESTest_test4 extends Md5Crypt_ESTest_scaffolding {

    /**
     * Verifies that {@link Md5Crypt#apr1Crypt(String, String)} produces the expected
     * Apache "$apr1$" MD5-based hash for a given plaintext and explicit salt.
     * <p>
     * The supplied salt "WU04K" is echoed back inside the resulting hash, which has
     * the standard form {@code $apr1$<salt>$<checksum>}.
     */
    @Test(timeout = 4000)
    public void apr1CryptWithExplicitSaltProducesExpectedHash() throws Throwable {
        final String plaintext = "~jShJLXp!+";
        final String salt = "WU04K";

        String hash = Md5Crypt.apr1Crypt(plaintext, salt);

        assertEquals("$apr1$WU04K$bMFrvq41gU5Hz2FhGeKhI.", hash);
    }
}
