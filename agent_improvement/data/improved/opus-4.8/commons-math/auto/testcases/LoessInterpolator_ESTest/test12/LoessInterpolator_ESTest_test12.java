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

    /**
     * Constructing a LoessInterpolator with an explicit bandwidth and number of
     * robustness iterations must not change the class-level default bandwidth
     * constant, which always stays at 0.3.
     */
    @Test(timeout = 4000)
    public void defaultBandwidthConstantIsUnaffectedByConstruction() throws Throwable {
        double customBandwidth = 0.6931470632553101;
        int robustnessIterations = 176;

        new LoessInterpolator(customBandwidth, robustnessIterations);

        double tolerance = 0.01;
        assertEquals(0.3, LoessInterpolator.DEFAULT_BANDWIDTH, tolerance);
    }
}
