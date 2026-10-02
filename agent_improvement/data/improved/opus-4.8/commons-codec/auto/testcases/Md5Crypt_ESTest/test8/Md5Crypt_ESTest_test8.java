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
public class Md5Crypt_ESTest_test8 extends Md5Crypt_ESTest_scaffolding {

    /**
     * Hashing an empty password with apr1Crypt should produce a deterministic
     * Apache "$apr1$" hash. The salt is generated from a mocked random source,
     * so both the salt ("........") and the resulting hash are reproducible.
     */
    @Test(timeout = 4000)
    public void apr1CryptOfEmptyStringReturnsExpectedHash() throws Throwable {
        final String emptyPassword = "";

        final String actualHash = Md5Crypt.apr1Crypt(emptyPassword);

        final String expectedHash = "$apr1$........$7DPFf0mVu8RHaTUUmUaFT.";
        assertEquals(expectedHash, actualHash);
    }
}
