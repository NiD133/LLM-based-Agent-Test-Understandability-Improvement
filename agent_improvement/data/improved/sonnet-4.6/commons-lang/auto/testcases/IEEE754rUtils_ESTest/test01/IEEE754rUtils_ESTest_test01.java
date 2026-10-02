package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test01 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that calling IEEE754rUtils.min(float[]) with an empty array
     * throws an IllegalArgumentException, as the contract requires at least
     * one element to compute a minimum.
     */
    @Test(timeout = 4000)
    public void test_minFloatArray_emptyArray_throwsIllegalArgumentException() throws Throwable {
        float[] emptyFloatArray = new float[0];

        try {
            IEEE754rUtils.min(emptyFloatArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
