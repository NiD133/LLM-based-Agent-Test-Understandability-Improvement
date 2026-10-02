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

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        byte[] byteArray0 = new byte[7];
        // Undeclared exception!
        try {
            Md5Crypt.md5Crypt(byteArray0, "$apr1$org.apache.commons.codec.digest.B64", "zHE)");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Invalid prefix value: zHE)
            //
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
