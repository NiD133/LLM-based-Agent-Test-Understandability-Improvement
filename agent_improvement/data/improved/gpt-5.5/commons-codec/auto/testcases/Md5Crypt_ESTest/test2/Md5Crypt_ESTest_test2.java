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
public class Md5Crypt_ESTest_test2 extends Md5Crypt_ESTest_scaffolding {

    private static final String EMPTY_SALT = "";
    private static final String INVALID_EMPTY_PREFIX = "";
    private static final long DETERMINISTIC_RANDOM_SEED = 0L;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] twoBytePassword = new byte[2];
        MockRandom deterministicRandom = new MockRandom(DETERMINISTIC_RANDOM_SEED);

        try {
            Md5Crypt.md5Crypt(twoBytePassword, EMPTY_SALT, INVALID_EMPTY_PREFIX, (Random) deterministicRandom);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
