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

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        byte[] byteArray0 = new byte[8];
        String string0 = Md5Crypt.apr1Crypt(byteArray0, (String) null);
        assertEquals("$apr1$........$qPSnH67sU5.ONzPrASfCP1", string0);
    }
}
