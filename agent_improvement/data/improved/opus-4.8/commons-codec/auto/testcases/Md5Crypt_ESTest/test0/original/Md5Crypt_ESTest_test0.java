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
public class Md5Crypt_ESTest_test0 extends Md5Crypt_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        byte[] byteArray0 = new byte[9];
        MockRandom mockRandom0 = new MockRandom((byte) (-21));
        // Undeclared exception!
        try {
            Md5Crypt.md5Crypt(byteArray0, "%=XSdI= ", "1%NP@$", (Random) mockRandom0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Invalid salt value: %=XSdI=
            //
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
