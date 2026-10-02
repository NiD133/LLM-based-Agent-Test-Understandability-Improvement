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
     * Calling {@code max} on an empty float array must be rejected, because there is
     * no value to return. The validation in {@link IEEE754rUtils#max(float...)} should
     * throw an {@link IllegalArgumentException} (raised by {@code org.apache.commons.lang3.Validate})
     * with the message "Array cannot be empty.".
     */
    @Test(timeout = 4000)
    public void maxOfEmptyFloatArrayThrowsIllegalArgumentException() throws Throwable {
        float[] emptyFloatArray = new float[0];

        try {
            IEEE754rUtils.max(emptyFloatArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.isTrue rejects the empty array with "Array cannot be empty."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
