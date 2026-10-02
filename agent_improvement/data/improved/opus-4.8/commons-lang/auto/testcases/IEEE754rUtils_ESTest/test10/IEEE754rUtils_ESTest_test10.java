package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test10 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link IEEE754rUtils#max(double, double, double)} returns the
     * largest of the three given values, regardless of their order.
     */
    @Test(timeout = 4000)
    public void maxOfThreeDoublesReturnsLargestValue() throws Throwable {
        final double largest = 734.1;
        final double middle = 331.69049072265625;
        final double smallest = 0.0;

        double result = IEEE754rUtils.max(largest, middle, smallest);

        assertEquals(largest, result, 0.01);
    }
}
