package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test06 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * With only 4 data points and the default bandwidth of 0.3,
     * the neighbourhood size is floor(0.3 * 4) = 1, which is too small.
     * LoessInterpolator must reject this with a RuntimeException
     * whose message contains "bandwidth (1)".
     */
    @Test(timeout = 4000)
    public void test06_smoothThrowsWhenBandwidthTooNarrowForDataSize() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();

        // Four x-values (also reused as y-values); the default bandwidth of 0.3
        // yields a window of only 1 point, which is invalid for LOESS smoothing.
        double[] xValues = new double[4];
        xValues[0] = 0.0;
        xValues[1] = 0.3;
        xValues[2] = 2.0;
        xValues[3] = 3745.0491385;

        try {
            interpolator.smooth(xValues, xValues);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Expected: "bandwidth (1)" — the computed window is too small
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
