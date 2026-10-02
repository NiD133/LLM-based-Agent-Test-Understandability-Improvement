package org.apache.commons.lang3.time;

import static org.junit.Assert.assertNotNull;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test16 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that the (deprecated) public no-arg constructor of
     * {@link DurationUtils} can be invoked and yields a usable instance.
     */
    @Test(timeout = 4000)
    public void constructorCreatesInstance() throws Throwable {
        DurationUtils durationUtils = new DurationUtils();

        assertNotNull(durationUtils);
    }
}
