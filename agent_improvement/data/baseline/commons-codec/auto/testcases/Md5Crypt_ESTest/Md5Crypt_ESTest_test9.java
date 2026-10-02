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

    @Test(timeout = 4000)
    public void test9() throws Throwable {
        byte[] byteArray0 = new byte[4];
        String string0 = Md5Crypt.md5Crypt(byteArray0);
        assertEquals("$1$........$EixpgL5t2L5AS7g5LlQUj.", string0);
    }
}
