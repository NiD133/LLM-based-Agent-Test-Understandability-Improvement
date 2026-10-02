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

    /**
     * md5Crypt requires a salt prefix of at least three characters (for example
     * "$1$"). When an empty prefix is supplied, the method must reject it with an
     * IllegalArgumentException reporting an invalid prefix value.
     */
    @Test(timeout = 4000)
    public void md5CryptWithEmptyPrefixThrowsIllegalArgumentException() throws Throwable {
        byte[] keyBytes = new byte[2];
        Random salt = new MockRandom(0L);
        String emptySalt = "";
        String emptyPrefix = "";

        try {
            Md5Crypt.md5Crypt(keyBytes, emptySalt, emptyPrefix, salt);
            fail("Expected IllegalArgumentException for an invalid (empty) prefix value");
        } catch (IllegalArgumentException e) {
            // Thrown by Md5Crypt because the prefix is shorter than the required 3 characters.
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
