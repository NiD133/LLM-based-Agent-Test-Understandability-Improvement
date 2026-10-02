package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test11 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that max(float, float, float) returns the largest of the three
     * arguments when the largest value is supplied as the final argument.
     */
    @Test(timeout = 4000)
    public void maxOfThreeFloatsReturnsLargestValue() throws Throwable {
        float largest = IEEE754rUtils.max(0.0F, 0.0F, 1488.587F);

        assertEquals(1488.587F, largest, 0.01F);
    }
}
