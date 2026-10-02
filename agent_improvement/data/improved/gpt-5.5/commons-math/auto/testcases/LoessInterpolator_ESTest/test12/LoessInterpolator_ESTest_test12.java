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

    private static final double BANDWIDTH = 0.6931470632553101;
    private static final int ROBUSTNESS_ITERATIONS = 176;
    private static final double EXPECTED_DEFAULT_BANDWIDTH = 0.3;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        LoessInterpolator interpolator =
                new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERATIONS);

        assertEquals(
                EXPECTED_DEFAULT_BANDWIDTH,
                LoessInterpolator.DEFAULT_BANDWIDTH,
                ASSERTION_TOLERANCE);
    }
}
