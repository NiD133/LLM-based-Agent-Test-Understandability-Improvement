package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test09 extends IEEE754rUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09_minOfThreeDoubles_returnsSmallestNonNaNValue() throws Throwable {
        double a = -1.0;
        double b = -1.0;
        double c = 0.0;

        double result = IEEE754rUtils.min(a, b, c);

        assertEquals("min(-1.0, -1.0, 0.0) should return -1.0 as the smallest value",
                -1.0, result, 0.01);
    }
}
