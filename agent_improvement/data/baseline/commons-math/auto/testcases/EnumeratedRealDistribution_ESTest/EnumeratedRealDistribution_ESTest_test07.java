package org.apache.commons.math4.legacy.distribution;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Random;
import org.apache.commons.rng.simple.JDKRandomWrapper;
import org.apache.commons.statistics.distribution.ContinuousDistribution;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumeratedRealDistribution_ESTest_test07 extends EnumeratedRealDistribution_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        double[] doubleArray0 = new double[2];
        EnumeratedRealDistribution enumeratedRealDistribution0 = new EnumeratedRealDistribution(doubleArray0);
        try {
            enumeratedRealDistribution0.inverseCumulativeProbability((-4414.9867));
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // -4,414.987 out of [0, 1] range
            //
            verifyException("org.apache.commons.math4.legacy.distribution.EnumeratedRealDistribution", e);
        }
    }
}
