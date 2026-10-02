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
    public void test0_md5CryptThrowsWhenSaltDoesNotMatchPrefix() throws Throwable {
        byte[] password = new byte[9];
        // Salt "%=XSdI= " does not start with the given prefix "1%NP@$",
        // so md5Crypt must reject it with IllegalArgumentException.
        String invalidSalt = "%=XSdI= ";
        String prefix = "1%NP@$";
        MockRandom mockRandom = new MockRandom((byte) (-21));

        try {
            Md5Crypt.md5Crypt(password, invalidSalt, prefix, (Random) mockRandom);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Invalid salt value: %=XSdI=
            //
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
