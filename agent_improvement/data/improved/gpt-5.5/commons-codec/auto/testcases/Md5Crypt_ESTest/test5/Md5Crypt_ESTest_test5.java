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
public class Md5Crypt_ESTest_test5 extends Md5Crypt_ESTest_scaffolding {

    private static final int EMPTY_KEY_LENGTH = 4;
    private static final byte DETERMINISTIC_RANDOM_SEED = (byte) 0;
    private static final String EXPECTED_APR1_HASH = "$apr1$........$bvww3NgecnxNN8NV12woN/";

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        byte[] emptyKeyBytes = new byte[EMPTY_KEY_LENGTH];
        MockRandom deterministicRandom = new MockRandom(DETERMINISTIC_RANDOM_SEED);

        String apr1Hash = Md5Crypt.apr1Crypt(emptyKeyBytes, (Random) deterministicRandom);

        assertEquals(EXPECTED_APR1_HASH, apr1Hash);
    }
}
