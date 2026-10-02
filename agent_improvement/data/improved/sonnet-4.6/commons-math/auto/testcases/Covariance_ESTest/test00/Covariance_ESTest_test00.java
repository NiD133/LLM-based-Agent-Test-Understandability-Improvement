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
public class Covariance_ESTest_test00 extends Covariance_ESTest_scaffolding {

    // Covariance requires at least 2 rows; a 1x1 matrix must be rejected.
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        double[][] singleRowSingleColumnMatrix = new double[1][1];
        Covariance covariance = null;
        try {
            covariance = new Covariance(singleRowSingleColumnMatrix);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }
}
