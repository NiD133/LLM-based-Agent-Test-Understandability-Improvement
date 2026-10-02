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

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        String string0 = Md5Crypt.apr1Crypt("");
        assertEquals("$apr1$........$7DPFf0mVu8RHaTUUmUaFT.", string0);
    }
}
