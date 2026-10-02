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

    /**
     * Verifies that apr1Crypt produces a deterministic "$apr1$" hash when given
     * a zero-filled 4-byte password and a seeded MockRandom for salt generation.
     * The MockRandom with seed 0 always generates the salt "........", yielding
     * the known hash "$apr1$........$bvww3NgecnxNN8NV12woN/".
     */
    @Test(timeout = 4000)
    public void testApr1CryptWithZeroBytesAndSeededRandom() throws Throwable {
        byte[] zeroFilledPassword = new byte[4];
        MockRandom seededRandom = new MockRandom((byte) 0);

        String apr1Hash = Md5Crypt.apr1Crypt(zeroFilledPassword, (Random) seededRandom);

        assertEquals("$apr1$........$bvww3NgecnxNN8NV12woN/", apr1Hash);
    }
}
