package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test04 extends LoessInterpolator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LoessInterpolator interpolator = new LoessInterpolator();

        double[] twoPointSeries = new double[2];
        twoPointSeries[1] = (double) 2;

        double[] smoothedSeries = interpolator.smooth(twoPointSeries, twoPointSeries);

        assertArrayEquals(new double[] { 0.0, 2.0 }, smoothedSeries, 0.01);
    }
}
