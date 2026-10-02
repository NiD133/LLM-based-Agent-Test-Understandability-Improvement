package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoessInterpolator_ESTest_test12 extends LoessInterpolator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        double customBandwidth = 0.6931470632553101;
        int robustnessIterations = 176;

        LoessInterpolator interpolator = new LoessInterpolator(customBandwidth, robustnessIterations);

        double expectedDefaultBandwidth = 0.3;
        double tolerance = 0.01;
        assertEquals(expectedDefaultBandwidth, LoessInterpolator.DEFAULT_BANDWIDTH, tolerance);
    }
}
