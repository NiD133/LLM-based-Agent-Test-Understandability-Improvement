package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test16 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that the deprecated public constructor of DurationUtils
     * can be instantiated without throwing an exception.
     */
    @Test(timeout = 4000)
    public void testConstructorInstantiatesSuccessfully() throws Throwable {
        DurationUtils durationUtils = new DurationUtils();
        assertNotNull(durationUtils);
    }
}
