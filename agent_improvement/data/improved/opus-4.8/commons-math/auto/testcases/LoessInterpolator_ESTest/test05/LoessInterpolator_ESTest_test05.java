package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test05 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * LOESS smoothing finishes by delegating to a SplineInterpolator, which
     * needs at least a few sample points. Interpolating a single (x, y) point
     * must therefore fail with a RuntimeException reporting "number of points (1)".
     */
    @Test(timeout = 4000)
    public void interpolateWithSinglePointThrowsTooFewPointsException() throws Throwable {
        LoessInterpolator loessInterpolator = new LoessInterpolator();

        // A single data point: both x and y have length 1.
        double[] singlePoint = new double[1];

        try {
            loessInterpolator.interpolate(singlePoint, singlePoint);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // The failure originates from SplineInterpolator, which rejects
            // having fewer points than it needs to build a spline.
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.SplineInterpolator", e);
        }
    }
}
