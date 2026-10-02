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
public class Md5Crypt_ESTest_test9 extends Md5Crypt_ESTest_scaffolding {

    private static final int ZERO_FILLED_KEY_LENGTH = 4;
    private static final String EXPECTED_MD5_CRYPT_HASH = "$1$........$EixpgL5t2L5AS7g5LlQUj.";

    @Test(timeout = 4000)
    public void test9() throws Throwable {
        byte[] zeroFilledKey = new byte[ZERO_FILLED_KEY_LENGTH];

        String actualHash = Md5Crypt.md5Crypt(zeroFilledKey);

        assertEquals(EXPECTED_MD5_CRYPT_HASH, actualHash);
    }
}
