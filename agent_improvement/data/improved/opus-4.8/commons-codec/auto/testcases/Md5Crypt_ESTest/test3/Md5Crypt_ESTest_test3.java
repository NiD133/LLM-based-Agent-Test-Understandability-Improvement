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
public class Md5Crypt_ESTest_test3 extends Md5Crypt_ESTest_scaffolding {

    /**
     * When {@link Md5Crypt#apr1Crypt(byte[], String)} is given a null salt, the method
     * generates one for us from a (here, deterministically mocked) SecureRandom and
     * produces an Apache "$apr1$" prefixed MD5 hash.
     *
     * With the mocked randomness the generated salt is always "........", so the full
     * hash for an all-zero 8-byte key is fixed and reproducible.
     */
    @Test(timeout = 4000)
    public void apr1CryptWithNullSaltGeneratesHashUsingRandomSalt() throws Throwable {
        byte[] emptyKey = new byte[8];

        String hash = Md5Crypt.apr1Crypt(emptyKey, (String) null);

        assertEquals("$apr1$........$qPSnH67sU5.ONzPrASfCP1", hash);
    }
}
