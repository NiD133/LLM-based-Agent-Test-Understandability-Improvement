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
public class Gaussian_ESTest_test0 extends Gaussian_ESTest_scaffolding {

    /**
     * Gaussian.Parametric.value(x, params) evaluates a Gaussian curve where
     * params = [normalization, mean, sigma]. With a zero normalization factor
     * the whole expression collapses to 0, regardless of x, mean or sigma.
     */
    @Test(timeout = 4000)
    public void valueWithZeroNormalizationIsZero() throws Throwable {
        Gaussian.Parametric gaussian = new Gaussian.Parametric();

        // params: normalization = 0.0, mean = 0.0, sigma = 2.0
        double[] parameters = { 0.0, 0.0, 2.0 };
        double x = 2.0;

        double result = gaussian.value(x, parameters);

        assertEquals(0.0, result, 0.01);
    }
}
