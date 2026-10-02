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
public class Md5Crypt_ESTest_test7 extends Md5Crypt_ESTest_scaffolding {

    /**
     * Verifies that {@link Md5Crypt#md5Crypt(byte[], Random)} produces a deterministic
     * "$1$" MD5-based crypt hash when the salt is drawn from a seeded, mocked {@link Random}.
     *
     * <p>Because {@link MockRandom} yields a fixed sequence, the random salt resolves to
     * "........", and hashing the two-byte (all-zero) key yields a reproducible hash string.</p>
     */
    @Test(timeout = 4000)
    public void md5CryptWithMockedRandomProducesDeterministicHash() throws Throwable {
        byte[] emptyTwoByteKey = new byte[2];
        Random deterministicRandom = new MockRandom();

        String hash = Md5Crypt.md5Crypt(emptyTwoByteKey, deterministicRandom);

        assertEquals("$1$........$bTtUql3/Wawqh.jbOopI01", hash);
    }
}
