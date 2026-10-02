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
public class EnumeratedRealDistribution_ESTest_test03 extends EnumeratedRealDistribution_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        double[] doubleArray0 = new double[4];
        EnumeratedRealDistribution enumeratedRealDistribution0 = new EnumeratedRealDistribution(doubleArray0);
        double double0 = enumeratedRealDistribution0.getVariance();
        assertEquals(0.0, double0, 0.01);
    }
}
