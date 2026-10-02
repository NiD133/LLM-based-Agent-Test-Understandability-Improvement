package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test03 extends LoessInterpolator_ESTest_scaffolding {

    private static final double BANDWIDTH_NEAR_ONE = 0.9999999999999997;
    private static final int ROBUSTNESS_ITERATIONS = 2;
    private static final double ACCURACY = 1.0;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        double[] samplePoints = new double[4];
        samplePoints[1] = 0.3;
        samplePoints[2] = (double) 2;
        samplePoints[3] = 3745.0491385;

        LoessInterpolator interpolator = new LoessInterpolator(
                BANDWIDTH_NEAR_ONE,
                ROBUSTNESS_ITERATIONS,
                ACCURACY);
        double[] smoothedValues = interpolator.smooth(samplePoints, samplePoints);

        double[] expectedSmoothedValues = new double[] {
                0.14923934718433918,
                0.1512398973385424,
                1.907630988400478,
                3745.0491290577315
        };
        assertArrayEquals(expectedSmoothedValues, smoothedValues, ASSERTION_TOLERANCE);
    }
}
