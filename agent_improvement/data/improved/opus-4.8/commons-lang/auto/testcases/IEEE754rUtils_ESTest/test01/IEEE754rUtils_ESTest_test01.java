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
     * Calling {@code min} on an empty float array is rejected: the method
     * validates the input via {@link org.apache.commons.lang3.Validate} and
     * throws an {@link IllegalArgumentException} ("Array cannot be empty.").
     */
    @Test(timeout = 4000)
    public void minOfEmptyFloatArrayThrowsIllegalArgumentException() throws Throwable {
        float[] emptyArray = new float[0];

        try {
            IEEE754rUtils.min(emptyArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown by Validate.isTrue when the array length is zero.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
