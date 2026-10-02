package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test11 extends IEEE754rUtils_ESTest_scaffolding {

    private static final float SMALLER_VALUE = 0.0F;
    private static final float EXPECTED_MAXIMUM = 1488.587F;
    private static final float FLOAT_COMPARISON_DELTA = 0.01F;

    @Test(timeout = 4000)
    public void maxReturnsLargestFloatWhenOneArgumentIsGreater() throws Throwable {
        float actualMaximum = IEEE754rUtils.max(SMALLER_VALUE, SMALLER_VALUE, EXPECTED_MAXIMUM);

        assertEquals(EXPECTED_MAXIMUM, actualMaximum, FLOAT_COMPARISON_DELTA);
    }
}
