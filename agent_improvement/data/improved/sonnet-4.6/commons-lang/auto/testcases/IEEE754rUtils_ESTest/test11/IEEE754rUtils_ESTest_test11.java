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

    @Test(timeout = 4000)
    public void test_maxOfThreeFloats_returnsLargestValue() throws Throwable {
        // Verify that max(a, b, c) returns c when c is the largest of the three float values
        float result = IEEE754rUtils.max(0.0F, 0.0F, 1488.587F);
        assertEquals(1488.587F, result, 0.01F);
    }
}
