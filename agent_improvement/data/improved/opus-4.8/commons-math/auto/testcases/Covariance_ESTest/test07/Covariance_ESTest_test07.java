package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest_test07 extends Covariance_ESTest_scaffolding {

    /**
     * The covariance matrix of a data set with N columns is always an N x N
     * (square) matrix. Here the data has a single column, so the resulting
     * covariance matrix must be 1 x 1 and therefore square.
     */
    @Test(timeout = 4000)
    public void computeCovarianceMatrixReturnsSquareMatrix() throws Throwable {
        // Data set: 13 observations (rows) with a single variable (column).
        double[][] singleColumnData = new double[13][1];

        boolean biasCorrected = false;
        Covariance covariance = new Covariance(singleColumnData, biasCorrected);

        RealMatrix covarianceMatrix = covariance.computeCovarianceMatrix(singleColumnData);

        assertTrue(covarianceMatrix.isSquare());
    }
}
