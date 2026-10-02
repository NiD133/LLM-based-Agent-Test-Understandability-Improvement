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
public class Md5Crypt_ESTest_test1 extends Md5Crypt_ESTest_scaffolding {

    private static final String APR1_STYLE_SALT = "$apr1$org.apache.commons.codec.digest.B64";
    private static final String INVALID_PREFIX = "zHE)";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        final byte[] keyBytes = new byte[7];

        try {
            Md5Crypt.md5Crypt(keyBytes, APR1_STYLE_SALT, INVALID_PREFIX);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
