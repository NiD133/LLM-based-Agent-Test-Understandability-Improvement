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
public class Md5Crypt_ESTest_test6 extends Md5Crypt_ESTest_scaffolding {

    // Md5Crypt exposes a deprecated public constructor; verify it can be instantiated without throwing.
    @Test(timeout = 4000)
    public void test_deprecatedConstructor_instantiatesWithoutException() throws Throwable {
        Md5Crypt md5Crypt0 = new Md5Crypt();
    }
}
