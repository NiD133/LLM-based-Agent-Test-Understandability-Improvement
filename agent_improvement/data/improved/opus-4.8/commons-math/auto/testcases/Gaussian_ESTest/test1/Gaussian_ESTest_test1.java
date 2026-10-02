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
     * Evaluating the standard Gaussian on a DerivativeStructure with a very large
     * input value yields (within tolerance) zero, because the Gaussian's bell curve
     * decays to 0 far from its mean.
     */
    @Test(timeout = 4000)
    public void valueOfGaussianAtLargeInputIsZero() throws Throwable {
        // Standard Gaussian: normalization = 1, mean = 0, standard deviation = 1.
        Gaussian standardGaussian = new Gaussian();

        // A DerivativeStructure with 0 free variables, order 12 and value 12.
        DerivativeStructure constantTwelve = new DerivativeStructure(0, 12, 12);

        // Raise 12 to the power of the above structure, producing a very large input.
        DerivativeStructure largeInput = DerivativeStructure.pow(12.0, constantTwelve);

        // The Gaussian decays to ~0 for inputs far from the mean.
        DerivativeStructure result = standardGaussian.value(largeInput);

        assertEquals(0.0, result.getValue(), 0.01);
    }
}
