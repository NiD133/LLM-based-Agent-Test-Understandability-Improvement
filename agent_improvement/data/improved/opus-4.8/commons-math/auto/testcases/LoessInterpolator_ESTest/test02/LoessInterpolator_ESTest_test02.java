package org.apache.commons.math4.legacy.analysis.interpolation;

import static org.junit.Assert.assertEquals;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test02 extends LoessInterpolator_ESTest_scaffolding {

    /**
     * Verifies that {@link LoessInterpolator#smooth(double[], double[], double[])}
     * returns one smoothed value per input abscissa, i.e. the result has the same
     * length as the supplied x/y data.
     */
    @Test(timeout = 4000)
    public void smoothReturnsOneValuePerInputPoint() throws Throwable {
        // Strictly increasing abscissas (required by LoessInterpolator).
        double[] xValues = {0.0, 0.3, 2.0, 3745.0491385};
        // Reuse the same array as the ordinates so x[i] == y[i] for every point.
        double[] yValues = xValues;
        // All-zero observation weights.
        double[] pointWeights = new double[7];

        LoessInterpolator interpolator = new LoessInterpolator(0.9999999999999997, 2, 1);
        double[] smoothedValues = interpolator.smooth(xValues, yValues, pointWeights);

        assertEquals(xValues.length, smoothedValues.length);
    }
}
