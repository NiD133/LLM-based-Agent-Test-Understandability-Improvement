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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        double[] emptyValues = new double[0];

        try {
            IEEE754rUtils.min(emptyValues);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // IEEE754rUtils delegates empty-array validation to Validate.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
