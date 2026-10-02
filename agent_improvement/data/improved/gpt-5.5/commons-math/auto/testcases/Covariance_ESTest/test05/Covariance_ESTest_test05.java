package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.legacy.linear.DiagonalMatrix;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test05 extends Covariance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Covariance emptyCovariance = new Covariance();

        int sampleCount = emptyCovariance.getN();

        assertEquals(0, sampleCount);
    }
}
