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

    private static final int EMPTY_PASSWORD_LENGTH = 8;
    private static final String NULL_SALT = null;
    private static final String EXPECTED_APR1_HASH = "$apr1$........$qPSnH67sU5.ONzPrASfCP1";

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        byte[] passwordBytes = new byte[EMPTY_PASSWORD_LENGTH];

        String actualHash = Md5Crypt.apr1Crypt(passwordBytes, NULL_SALT);

        assertEquals(EXPECTED_APR1_HASH, actualHash);
    }
}
