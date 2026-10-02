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

    /**
     * A default Gaussian uses mean = 0 and standard deviation = 1. Evaluating it
     * far out in the tail (here x = -2892.35) should yield a value that is
     * effectively zero.
     */
    @Test(timeout = 4000)
    public void valueFarInTailIsEffectivelyZero() throws Throwable {
        Gaussian standardGaussian = new Gaussian();

        double valueInFarTail = standardGaussian.value(-2892.35172268);

        double expectedValue = 0.0;
        double tolerance = 0.01;
        assertEquals(expectedValue, valueInFarTail, tolerance);
    }
}
