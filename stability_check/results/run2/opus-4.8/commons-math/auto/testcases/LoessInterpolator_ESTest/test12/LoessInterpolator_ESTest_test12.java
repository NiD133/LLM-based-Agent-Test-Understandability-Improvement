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

    /** Bandwidth used when building the interpolator (an arbitrary valid fraction in (0, 1]). */
    private static final double BANDWIDTH = 0.6931470632553101;

    /** Number of robustness iterations passed to the constructor. */
    private static final int ROBUSTNESS_ITERS = 176;

    /** Expected value of the class-level default bandwidth constant. */
    private static final double EXPECTED_DEFAULT_BANDWIDTH = 0.3;

    /** Tolerance for the floating-point comparison. */
    private static final double TOLERANCE = 0.01;

    /**
     * Constructing a LoessInterpolator with a custom bandwidth and iteration count
     * must not change the shared DEFAULT_BANDWIDTH constant, which should remain 0.3.
     */
    @Test(timeout = 4000)
    public void defaultBandwidthConstantIsUnaffectedByCustomConstruction() throws Throwable {
        new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERS);

        assertEquals(EXPECTED_DEFAULT_BANDWIDTH, LoessInterpolator.DEFAULT_BANDWIDTH, TOLERANCE);
    }
}
