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

    /**
     * Computing the covariance matrix of an empty (0x0) matrix must fail, because
     * the covariance of a single column requires at least one row of data. The
     * dimension check inside AbstractRealMatrix rejects the empty matrix with a
     * RuntimeException before any covariance is computed.
     */
    @Test(timeout = 4000)
    public void computeCovarianceMatrixOnEmptyMatrixThrowsRuntimeException() throws Throwable {
        // A 13-row, single-column data set is enough to construct the Covariance
        // instance; biasCorrected is disabled so construction succeeds without
        // requiring more rows.
        double[][] singleColumnData = new double[13][1];
        Covariance covariance = new Covariance(singleColumnData, false);

        // An empty matrix has zero columns, which violates the minimum-dimension
        // requirement of computeCovarianceMatrix.
        Array2DRowRealMatrix emptyMatrix = new Array2DRowRealMatrix();

        try {
            covariance.computeCovarianceMatrix(emptyMatrix);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Message: "0 is smaller than, or equal to, the minimum (0)"
            verifyException("org.apache.commons.math4.legacy.linear.AbstractRealMatrix", e);
        }
    }
}
