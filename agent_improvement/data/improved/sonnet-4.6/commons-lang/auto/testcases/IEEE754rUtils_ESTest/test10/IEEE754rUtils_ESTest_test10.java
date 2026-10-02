package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test10 extends IEEE754rUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10_maxOfThreeDoubles_returnsLargest() throws Throwable {
        double largestValue  = 734.1;
        double middleValue   = 331.69049072265625;
        double smallestValue = (double) 0.0F;

        double result = IEEE754rUtils.max(largestValue, middleValue, smallestValue);

        assertEquals(largestValue, result, 0.01);
    }
}
