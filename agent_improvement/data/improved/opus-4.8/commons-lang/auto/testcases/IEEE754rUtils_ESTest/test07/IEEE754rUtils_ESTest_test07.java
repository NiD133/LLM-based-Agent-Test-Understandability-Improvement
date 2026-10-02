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
     * Verifies that {@link IEEE754rUtils#max(double...)} rejects an empty array.
     * Validate.isTrue inside the method throws an IllegalArgumentException
     * carrying the message "Array cannot be empty.".
     */
    @Test(timeout = 4000)
    public void maxOfEmptyDoubleArrayThrowsIllegalArgumentException() throws Throwable {
        double[] emptyArray = new double[0];

        try {
            IEEE754rUtils.max(emptyArray);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The validation that rejects the empty array lives in Validate.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
