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
public class Md5Crypt_ESTest_test5 extends Md5Crypt_ESTest_scaffolding {

    /**
     * Verifies that {@link Md5Crypt#apr1Crypt(byte[], Random)} produces a deterministic
     * Apache "$apr1$" hash when the salt is generated from a seeded (mock) Random.
     * <p>
     * Because the Random is seeded with a fixed value, both the random salt
     * ("........") and the resulting hash are reproducible.
     * </p>
     */
    @Test(timeout = 4000)
    public void apr1CryptWithSeededRandomProducesExpectedHash() throws Throwable {
        // An all-zero, four-byte key (empty/zeroed password input).
        byte[] keyBytes = new byte[4];

        // Seeded Random so the generated salt is deterministic.
        MockRandom seededRandom = new MockRandom((byte) 0);

        String hash = Md5Crypt.apr1Crypt(keyBytes, (Random) seededRandom);

        assertEquals("$apr1$........$bvww3NgecnxNN8NV12woN/", hash);
    }
}
