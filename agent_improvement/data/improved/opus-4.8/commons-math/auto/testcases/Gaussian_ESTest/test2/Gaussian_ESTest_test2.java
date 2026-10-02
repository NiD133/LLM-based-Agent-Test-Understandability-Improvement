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
public class Gaussian_ESTest_test2 extends Gaussian_ESTest_scaffolding {

    /**
     * Verifies that the standard Gaussian (mean = 0, sigma = 1, normalized peak = 1)
     * can be evaluated through the {@link DerivativeStructure} overload.
     *
     * Evaluating at x = 12 yields exp(-12^2 / 2) = exp(-72) ~= 2.146e-32,
     * i.e. the curve has effectively decayed to zero far out in the tail.
     */
    @Test(timeout = 4000)
    public void valueAtFarTailIsEffectivelyZero() throws Throwable {
        Gaussian standardGaussian = new Gaussian();

        // A constant DerivativeStructure holding the value 12 (no free variables, order 12).
        DerivativeStructure inputAtTwelve = new DerivativeStructure(0, 12, 12);

        DerivativeStructure result = standardGaussian.value(inputAtTwelve);

        double expectedValue = 2.1463837356630605E-32;
        assertEquals(expectedValue, result.getValue(), 0.01);
    }
}
