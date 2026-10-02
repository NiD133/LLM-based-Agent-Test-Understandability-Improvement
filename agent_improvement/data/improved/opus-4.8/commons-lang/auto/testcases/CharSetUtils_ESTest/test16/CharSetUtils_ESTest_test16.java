package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test16 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that the public (deprecated) CharSetUtils constructor can be
     * invoked without throwing. The constructor exists only to support tools
     * that require a JavaBean instance, so simply creating an object is enough.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstanceWithoutError() throws Throwable {
        CharSetUtils charSetUtils = new CharSetUtils();

        assertNotNull(charSetUtils);
    }
}
