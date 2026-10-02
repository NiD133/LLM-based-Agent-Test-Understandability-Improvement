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

    /**
     * md5Crypt extracts the real salt by matching it against the given prefix.
     * Here the salt "%=XSdI= " does not start with the prefix "1%NP@$", so the
     * internal pattern fails to match and md5Crypt rejects the salt with an
     * IllegalArgumentException ("Invalid salt value: ...").
     */
    @Test(timeout = 4000)
    public void md5CryptWithSaltNotMatchingPrefixThrowsIllegalArgument() throws Throwable {
        byte[] keyBytes = new byte[9];
        Random random = new MockRandom((byte) -21);
        String mismatchedSalt = "%=XSdI= ";
        String prefix = "1%NP@$";

        try {
            Md5Crypt.md5Crypt(keyBytes, mismatchedSalt, prefix, random);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Invalid salt value: %=XSdI=
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
