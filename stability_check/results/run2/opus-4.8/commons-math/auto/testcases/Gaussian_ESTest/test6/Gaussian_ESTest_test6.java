package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Gaussian_ESTest_test6 extends Gaussian_ESTest_scaffolding {

    /**
     * The Gaussian constructor requires a strictly positive standard deviation
     * (sigma). Passing sigma = 0 must be rejected, because a value of 0 is not
     * greater than the required minimum of 0.
     */
    @Test(timeout = 4000)
    public void constructorRejectsZeroStandardDeviation() throws Throwable {
        final double mean = 1193.809784545181;
        final double nonPositiveSigma = 0.0;

        try {
            new Gaussian(mean, nonPositiveSigma);
            fail("Expected a RuntimeException because sigma (0) is not strictly positive");
        } catch (RuntimeException expected) {
            // Message: "0 is smaller than, or equal to, the minimum (0)"
            verifyException("org.apache.commons.math4.legacy.analysis.function.Gaussian", expected);
        }
    }
}
