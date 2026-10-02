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
     * Verifies that {@code smooth} rejects inputs whose x and y arrays have
     * different lengths, since it requires one weight/value per abscissa.
     */
    @Test(timeout = 4000)
    public void smoothWithMismatchedArrayLengthsThrowsDimensionMismatch() throws Throwable {
        LoessInterpolator loessInterpolator = new LoessInterpolator();

        // x and weights have length 4 but y has length 7 -> dimension mismatch.
        double[] xValues = new double[4];
        double[] yValues = new double[7];
        double[] weights = xValues;

        try {
            loessInterpolator.smooth(xValues, yValues, weights);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Message reports the mismatching lengths: "4 != 7".
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
