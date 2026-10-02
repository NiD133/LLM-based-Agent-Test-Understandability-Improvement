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
public class Gaussian_ESTest_test1 extends Gaussian_ESTest_scaffolding {

    /**
     * Verifies that the default Gaussian (mean=0, sigma=1) evaluates to
     * approximately zero when the input is extremely far from the mean.
     *
     * 12^12 is used as the input: a DerivativeStructure of order 12 wrapping
     * the constant 12 is raised to the power 12, yielding a value ~8.9e12.
     * At that distance from the mean the bell-curve density is indistinguishable
     * from zero within a tolerance of 0.01.
     */
    @Test(timeout = 4000)
    public void testGaussianValueAtLargeInputApproachesZero() throws Throwable {
        // Default Gaussian: mean=0, sigma=1, norm=1/(sigma*sqrt(2*pi))
        Gaussian gaussian = new Gaussian();

        // Build the constant 12 as a DerivativeStructure (0 free variables, order 12)
        DerivativeStructure constantTwelve = new DerivativeStructure(0, 12, 12);

        // Raise 12 (as a double base) to the power of the DerivativeStructure,
        // producing a very large value (12^12 ≈ 8.9e12) far from the mean
        DerivativeStructure largeInput = DerivativeStructure.pow((double) 12, constantTwelve);

        // Evaluate the Gaussian at 12^12 — expected to be essentially zero
        DerivativeStructure result = gaussian.value(largeInput);

        assertEquals(0.0, result.getValue(), 0.01);
    }
}
