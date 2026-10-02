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
public class Covariance_ESTest_test07 extends Covariance_ESTest_scaffolding {

    // A covariance matrix is always square (N variables → N×N result), regardless of
    // the number of observations. This test verifies that property holds when
    // computeCovarianceMatrix is called on a dataset of 13 observations × 1 variable.
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // 13 observations, each with 1 variable (all zero-valued by default)
        int numObservations = 13;
        int numVariables = 1;
        double[][] observationData = new double[numObservations][numVariables];

        // biasCorrection=false means we divide by N instead of N-1
        boolean biasCorrection = false;
        Covariance covariance = new Covariance(observationData, biasCorrection);

        // The resulting covariance matrix must be numVariables × numVariables (1×1 here)
        RealMatrix covarianceMatrix = covariance.computeCovarianceMatrix(observationData);

        assertTrue(covarianceMatrix.isSquare());
    }
}
