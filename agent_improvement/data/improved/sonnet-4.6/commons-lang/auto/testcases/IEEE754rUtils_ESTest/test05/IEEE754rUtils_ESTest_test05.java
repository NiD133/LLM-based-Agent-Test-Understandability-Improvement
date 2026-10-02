package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test05 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that IEEE754rUtils.max(float[]) throws IllegalArgumentException
     * when called with an empty array, as required by the API contract.
     */
    @Test(timeout = 4000)
    public void test05_maxFloatArray_throwsOnEmptyArray() throws Throwable {
        float[] emptyFloatArray = new float[0];

        try {
            IEEE754rUtils.max(emptyFloatArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
