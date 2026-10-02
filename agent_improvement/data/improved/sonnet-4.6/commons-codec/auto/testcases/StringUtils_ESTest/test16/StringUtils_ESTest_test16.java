package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test16 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testDeprecatedPublicConstructorCreatesInstance() throws Throwable {
        // StringUtils exposes a deprecated no-arg constructor; verify it can be instantiated
        StringUtils stringUtils0 = new StringUtils();
        assertNotNull(stringUtils0);
    }
}
