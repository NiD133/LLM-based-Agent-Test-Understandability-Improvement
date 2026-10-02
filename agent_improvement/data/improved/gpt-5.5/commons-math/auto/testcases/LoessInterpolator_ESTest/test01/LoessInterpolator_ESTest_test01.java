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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        double[] xValuesAndWeights = new double[] {
            0.0,
            0.3,
            2.0,
            3745.0491385
        };
        double[] yValues = new double[4];
        yValues[2] = 1.0;

        LoessInterpolator interpolator = new LoessInterpolator(1.0, 2, (-1094.0));

        double[] smoothedValues = interpolator.smooth(xValuesAndWeights, yValues, xValuesAndWeights);

        assertArrayEquals(
            new double[] { (-0.1764705882352937), 7.216449660063518E-16, 1.0, 0.0 },
            smoothedValues,
            0.01);
    }
}
