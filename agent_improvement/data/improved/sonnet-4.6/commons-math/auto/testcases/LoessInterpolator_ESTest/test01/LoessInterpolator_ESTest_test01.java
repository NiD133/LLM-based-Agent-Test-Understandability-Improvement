package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test01 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Verifies that LoessInterpolator.smooth() produces the expected smoothed y-values
     * when given non-uniform x-coordinates, partially-set y-values, and x-values as weights.
     *
     * The interpolator is configured with bandwidth=1.0 (full span), robustnessIters=2,
     * and accuracy=-1094.0. The weight array equals the x-coordinate array.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // x-coordinates: [0.0, 0.3, 2.0, 3745.0491385]
        double[] xValues = new double[4];
        xValues[1] = 0.3;
        xValues[2] = (double) 2;
        xValues[3] = 3745.0491385;

        // Interpolator: bandwidth=1.0 (use all points), 2 robustness iterations, accuracy=-1094.0
        LoessInterpolator loessInterpolator = new LoessInterpolator(1.0, 2, (-1094.0));

        // y-values: [0.0, 0.0, 1.0, 0.0] — only the third point has a non-zero value
        double[] yValues = new double[4];
        yValues[2] = 1.0;

        // weights equal the x-coordinates
        double[] weights = xValues;

        double[] smoothedValues = loessInterpolator.smooth(xValues, yValues, weights);

        double[] expectedSmoothedValues = { (-0.1764705882352937), 7.216449660063518E-16, 1.0, 0.0 };
        assertArrayEquals(expectedSmoothedValues, smoothedValues, 0.01);
    }
}
