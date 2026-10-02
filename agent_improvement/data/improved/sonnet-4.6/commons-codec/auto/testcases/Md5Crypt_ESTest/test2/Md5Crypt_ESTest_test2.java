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
     * Verifies that md5Crypt rejects an empty prefix string.
     *
     * The four-argument overload requires the prefix to be at least 3 characters
     * long and delimited by '$' (e.g. "$1$" or "$apr1$"). Passing an empty string
     * violates that contract, so an IllegalArgumentException must be thrown.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        byte[] passwordBytes = new byte[2];
        String emptySalt   = "";
        String emptyPrefix = "";
        MockRandom mockRandom = new MockRandom(0L);

        try {
            Md5Crypt.md5Crypt(passwordBytes, emptySalt, emptyPrefix, (Random) mockRandom);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // "Invalid prefix value:" is thrown by Md5Crypt when the prefix is too short
            verifyException("org.apache.commons.codec.digest.Md5Crypt", e);
        }
    }
}
