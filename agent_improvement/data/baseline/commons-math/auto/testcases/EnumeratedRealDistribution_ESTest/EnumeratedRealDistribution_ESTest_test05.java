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
public class EnumeratedRealDistribution_ESTest_test05 extends EnumeratedRealDistribution_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        double[] doubleArray0 = new double[4];
        doubleArray0[1] = 1151.5604806092329;
        EnumeratedRealDistribution enumeratedRealDistribution0 = new EnumeratedRealDistribution(doubleArray0, doubleArray0);
        double double0 = enumeratedRealDistribution0.inverseCumulativeProbability(0.24813660340178628);
        assertEquals(1151.5604806092329, double0, 0.01);
    }
}
