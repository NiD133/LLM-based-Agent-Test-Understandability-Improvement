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
     * Verifies that interpolating with only a single data point throws a RuntimeException,
     * because the underlying SplineInterpolator requires at least 3 points to fit a curve.
     */
    @Test(timeout = 4000)
    public void test05_interpolateWithSinglePoint_throwsRuntimeException() throws Throwable {
        LoessInterpolator loessInterpolator = new LoessInterpolator();

        // A single-element array is insufficient for spline interpolation
        double[] singlePointX = new double[1];
        double[] singlePointY = new double[1];

        try {
            loessInterpolator.interpolate(singlePointX, singlePointY);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // SplineInterpolator requires more than 1 point; error message includes "number of points (1)"
            verifyException("org.apache.commons.math4.legacy.analysis.interpolation.SplineInterpolator", e);
        }
    }
}
