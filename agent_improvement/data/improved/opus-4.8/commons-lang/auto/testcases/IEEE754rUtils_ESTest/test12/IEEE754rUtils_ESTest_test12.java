package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test12 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that the (deprecated) no-arg constructor of {@link IEEE754rUtils}
     * can be invoked and produces a non-null instance.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        IEEE754rUtils instance = new IEEE754rUtils();

        assertNotNull(instance);
    }
}
