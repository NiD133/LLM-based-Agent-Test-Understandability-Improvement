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

    // The expected hash produced by apr1Crypt when given an empty password;
    // the salt "........" is deterministic under EvoSuite's mocked RNG.
    private static final String EXPECTED_APR1_HASH_FOR_EMPTY_PASSWORD = "$apr1$........$7DPFf0mVu8RHaTUUmUaFT.";

    @Test(timeout = 4000)
    public void test_apr1Crypt_withEmptyPassword_returnsExpectedHash() throws Throwable {
        String hashedPassword = Md5Crypt.apr1Crypt("");
        assertEquals(EXPECTED_APR1_HASH_FOR_EMPTY_PASSWORD, hashedPassword);
    }
}
