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
public class Md5Crypt_ESTest_test3 extends Md5Crypt_ESTest_scaffolding {

    /**
     * When a null salt is passed, apr1Crypt generates a salt internally via SecureRandom.
     * The mocked JVM makes SecureRandom deterministic, so the generated salt is always
     * "........", producing a predictable APR1 hash for the all-zero password bytes.
     */
    @Test(timeout = 4000)
    public void test_apr1Crypt_withNullSalt_generatesSaltAndReturnsExpectedHash() throws Throwable {
        byte[] allZeroPassword = new byte[8];
        String expectedHash = "$apr1$........$qPSnH67sU5.ONzPrASfCP1";

        String actualHash = Md5Crypt.apr1Crypt(allZeroPassword, (String) null);

        assertEquals(expectedHash, actualHash);
    }
}
