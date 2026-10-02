package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test07 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link IEEE754rUtils#max(double[])} throws an
     * {@link IllegalArgumentException} when called with an empty array.
     * The contract requires at least one element so that a meaningful
     * maximum can be computed.
     */
    @Test(timeout = 4000)
    public void test_max_emptyDoubleArray_throwsIllegalArgumentException() throws Throwable {
        double[] emptyArray = new double[0];

        try {
            IEEE754rUtils.max(emptyArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
