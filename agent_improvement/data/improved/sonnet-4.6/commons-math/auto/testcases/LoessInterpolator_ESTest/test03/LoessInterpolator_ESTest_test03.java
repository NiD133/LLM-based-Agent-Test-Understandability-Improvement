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

    // Verifies that smooth() produces expected output when the same array is used for both x and y values
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        double[] dataPoints = new double[4];
        dataPoints[1] = 0.3;
        dataPoints[2] = 2.0;
        dataPoints[3] = 3745.0491385;

        // bandwidth ~1.0, robustnessIters=2, accuracy=1
        LoessInterpolator interpolator = new LoessInterpolator(0.9999999999999997, 2, 1);

        double[] smoothedValues = interpolator.smooth(dataPoints, dataPoints);

        assertArrayEquals(
            new double[] { 0.14923934718433918, 0.1512398973385424, 1.907630988400478, 3745.0491290577315 },
            smoothedValues,
            0.01
        );
    }
}
