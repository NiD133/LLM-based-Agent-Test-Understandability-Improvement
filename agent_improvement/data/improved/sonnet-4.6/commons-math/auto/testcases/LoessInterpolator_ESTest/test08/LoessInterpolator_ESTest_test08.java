package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test08 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Verifies that smooth() throws a RuntimeException when the xval and yval arrays
     * have different lengths (4 vs 7). The weights array has the same length as xval,
     * but the mismatch between xval and yval is what triggers the error.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LoessInterpolator loessInterpolator = new LoessInterpolator();

        double[] xval = new double[4];
        double[] yval = new double[7];
        double[] weights = new double[4];

        try {
            loessInterpolator.smooth(xval, yval, weights);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Expected: xval.length (4) != yval.length (7)
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
