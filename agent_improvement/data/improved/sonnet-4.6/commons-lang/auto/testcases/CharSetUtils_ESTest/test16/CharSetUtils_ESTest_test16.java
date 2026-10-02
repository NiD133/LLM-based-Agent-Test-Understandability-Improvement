package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test16 extends CharSetUtils_ESTest_scaffolding {

    // CharSetUtils exposes a deprecated public no-arg constructor to support JavaBean
    // tooling. This test verifies that instantiation succeeds without throwing.
    @Test(timeout = 4000)
    public void test_deprecatedConstructor_instantiatesSuccessfully() throws Throwable {
        CharSetUtils charSetUtils0 = new CharSetUtils();
        assertNotNull(charSetUtils0);
    }
}
