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
public class Covariance_ESTest_test06 extends Covariance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        double[][] doubleArray0 = new double[13][1];
        Covariance covariance0 = new Covariance(doubleArray0, false);
        Array2DRowRealMatrix array2DRowRealMatrix0 = new Array2DRowRealMatrix();
        try {
            covariance0.computeCovarianceMatrix(array2DRowRealMatrix0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // 0 is smaller than, or equal to, the minimum (0)
            //
            verifyException("org.apache.commons.math4.legacy.linear.AbstractRealMatrix", e);
        }
    }
}
