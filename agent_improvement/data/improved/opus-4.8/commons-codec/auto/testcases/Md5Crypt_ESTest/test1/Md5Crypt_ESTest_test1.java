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
     * A valid prefix must either start or end with '$' (for example "$1$" or "$apr1$").
     * Here the prefix "zHE)" satisfies neither rule, so md5Crypt must reject it with an
     * IllegalArgumentException ("Invalid prefix value: zHE)").
     */
    @Test(timeout = 4000)
    public void md5CryptWithMalformedPrefixThrowsIllegalArgumentException() throws Throwable {
        byte[] keyBytes = new byte[7];
        String saltContainingApr1Prefix = "$apr1$org.apache.commons.codec.digest.B64";
        String invalidPrefix = "zHE)";

        try {
            Md5Crypt.md5Crypt(keyBytes, saltContainingApr1Prefix, invalidPrefix);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Invalid prefix value: zHE)
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
