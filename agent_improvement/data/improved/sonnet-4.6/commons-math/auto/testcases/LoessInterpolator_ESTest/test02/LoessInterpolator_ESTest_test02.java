package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test02 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Verifies that smooth() returns an array with the same length as the input xval/yval arrays.
     *
     * The LoessInterpolator is configured with bandwidth ~1.0, 2 robustness iterations,
     * and accuracy 1. The x-values are [0.0, 0.3, 2.0, 3745.0], which are strictly
     * increasing as required. The weights array has more elements than xval/yval (7 vs 4),
     * which is intentional — smooth() only reads the first 4 weights and returns a result
     * array of length 4 (matching the number of data points).
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Four x-values that are strictly increasing (required by LoessInterpolator)
        double[] xval = new double[4];
        xval[0] = 0.0;
        xval[1] = 0.3;
        xval[2] = 2.0;
        xval[3] = 3745.0491385;

        // Use the same array as y-values so each y equals its corresponding x
        double[] yval = xval;

        // Bandwidth near 1.0, 2 robustness iterations, accuracy threshold of 1
        LoessInterpolator interpolator = new LoessInterpolator(0.9999999999999997, 2, 1);

        // Weights array is longer than xval/yval; smooth() reads only the first 4 entries
        double[] weights = new double[7];

        double[] smoothed = interpolator.smooth(xval, yval, weights);

        // The output array must have one smoothed value per input data point
        assertEquals(4, smoothed.length);
    }
}
