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
public class Covariance_ESTest_test02 extends Covariance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Covariance covariance0 = new Covariance();
        double[] doubleArray0 = new double[1];
        try {
            covariance0.covariance(doubleArray0, doubleArray0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // sample contains 1 observed points, at least 2 are required
            //
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
