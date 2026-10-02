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

    /**
     * Verifies that md5Crypt throws IllegalArgumentException when given a prefix
     * that does not start and end with '$' (e.g. "zHE)" is not a valid prefix).
     */
    @Test(timeout = 4000)
    public void test1_md5Crypt_invalidPrefix_throwsIllegalArgumentException() throws Throwable {
        byte[] password = new byte[7];
        String saltWithApr1Prefix = "$apr1$org.apache.commons.codec.digest.B64";
        String invalidPrefix = "zHE)";

        try {
            Md5Crypt.md5Crypt(password, saltWithApr1Prefix, invalidPrefix);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: prefix must start with '$' and end with '$'
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
