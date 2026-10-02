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
public class Covariance_ESTest_test01 extends Covariance_ESTest_scaffolding {

    // Number of rows in the input data matrix, which determines the sample count N.
    private static final int ROW_COUNT = 6;
    private static final int COL_COUNT = 7;

    @Test(timeout = 4000)
    public void test_getN_returnsNumberOfRows_whenBiasedCovarianceConstructed() throws Throwable {
        double[][] inputData = new double[ROW_COUNT][COL_COUNT];

        // biasCorrected=false means population covariance; N equals the row count.
        Covariance covariance = new Covariance(inputData, false);

        assertEquals(ROW_COUNT, covariance.getN());
    }
}
