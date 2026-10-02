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

    // DEFAULT_BANDWIDTH is a class-level constant; constructing an instance should not affect it.
    @Test(timeout = 4000)
    public void test_defaultBandwidthConstantIsUnaffectedByCustomConstructor() throws Throwable {
        double customBandwidth = 0.6931470632553101;
        int robustnessIterations = 176;
        LoessInterpolator interpolatorWithCustomParams = new LoessInterpolator(customBandwidth, robustnessIterations);

        assertEquals(
            "DEFAULT_BANDWIDTH should remain 0.3 regardless of custom-constructed instances",
            0.3,
            LoessInterpolator.DEFAULT_BANDWIDTH,
            0.01
        );
    }
}
