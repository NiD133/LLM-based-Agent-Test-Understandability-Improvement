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
public class Covariance_ESTest_test03 extends Covariance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Covariance covariance = new Covariance();
        double[] singleValueSample = new double[1];
        double[] twoValueSample = new double[2];

        try {
            covariance.covariance(twoValueSample, singleValueSample);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 2 != 1
            //
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
