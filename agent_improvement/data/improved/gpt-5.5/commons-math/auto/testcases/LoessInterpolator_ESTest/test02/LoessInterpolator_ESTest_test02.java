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

    private static final int SAMPLE_COUNT = 4;
    private static final int WEIGHT_COUNT = 7;
    private static final int EXPECTED_SMOOTHED_LENGTH = 4;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        double[] xValuesAndObservedValues = new double[SAMPLE_COUNT];
        xValuesAndObservedValues[1] = 0.3;
        xValuesAndObservedValues[2] = (double) 2;
        xValuesAndObservedValues[3] = 3745.0491385;

        LoessInterpolator interpolator = new LoessInterpolator(0.9999999999999997, 2, 1);
        double[] unitWeights = new double[WEIGHT_COUNT];

        double[] smoothedValues = interpolator.smooth(xValuesAndObservedValues, xValuesAndObservedValues, unitWeights);

        assertEquals(EXPECTED_SMOOTHED_LENGTH, smoothedValues.length);
    }
}
