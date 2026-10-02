package org.apache.commons.lang3.math;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IEEE754rUtils_ESTest_test03 extends IEEE754rUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link IEEE754rUtils#min(double...)} rejects an empty array
     * by throwing an {@link IllegalArgumentException}, since there is no value
     * to compute a minimum from. The validation is performed by
     * {@code org.apache.commons.lang3.Validate}.
     */
    @Test(timeout = 4000)
    public void minOfEmptyDoubleArrayThrowsIllegalArgumentException() throws Throwable {
        double[] emptyArray = new double[0];

        try {
            IEEE754rUtils.min(emptyArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate rejects the empty array with message "Array cannot be empty."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
