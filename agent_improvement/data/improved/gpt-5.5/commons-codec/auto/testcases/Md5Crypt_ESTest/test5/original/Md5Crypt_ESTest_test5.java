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

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        byte[] byteArray0 = new byte[4];
        MockRandom mockRandom0 = new MockRandom((byte) 0);
        String string0 = Md5Crypt.apr1Crypt(byteArray0, (Random) mockRandom0);
        assertEquals("$apr1$........$bvww3NgecnxNN8NV12woN/", string0);
    }
}
