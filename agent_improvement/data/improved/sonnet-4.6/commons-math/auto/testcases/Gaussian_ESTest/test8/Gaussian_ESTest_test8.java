package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Gaussian_ESTest_test8 extends Gaussian_ESTest_scaffolding {

    // Far-tail input: a large negative x where the standard Gaussian value is effectively 0
    private static final double FAR_NEGATIVE_X = -2892.35172268;
    private static final double TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test_defaultGaussian_atFarNegativeX_returnsZero() throws Throwable {
        Gaussian gaussian = new Gaussian();
        double result = gaussian.value(FAR_NEGATIVE_X);
        assertEquals(0.0, result, TOLERANCE);
    }
}
