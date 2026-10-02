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
     * Smoothing a data set that contains only four points fails: with the
     * default bandwidth the interpolator needs at least two points inside the
     * smoothing window, but four points round down to a window size of one.
     * The interpolator should reject this by throwing a RuntimeException whose
     * message reports the offending bandwidth window size of one.
     */
    @Test(timeout = 4000)
    public void smoothFailsWhenBandwidthWindowHoldsOnlyOnePoint() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();

        // Four abscissa/ordinate values shared as both x and y arguments.
        double[] points = new double[4];
        points[1] = 0.3;
        points[2] = 2.0;
        points[3] = 3745.0491385;

        try {
            interpolator.smooth(points, points);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The interpolator complains that the effective bandwidth is 1.
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.LoessInterpolator", e);
        }
    }
}
