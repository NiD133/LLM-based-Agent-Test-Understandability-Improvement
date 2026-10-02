package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.legacy.linear.DiagonalMatrix;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.apache.commons.math4.legacy.stat.correlation.Covariance;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Covariance_ESTest extends Covariance_ESTest_scaffolding {

    // Constructor: double[][] input requires at least 2 rows
    @Test(timeout = 4000)
    public void constructorWithSingleRowMatrix_throwsRuntimeException() throws Throwable {
        double[][] singleRowMatrix = new double[1][1];
        try {
            new Covariance(singleRowMatrix);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }

    // Constructor with biasCorrected=false stores N (number of rows)
    @Test(timeout = 4000)
    public void constructorWithMatrix_biasCorrectedFalse_storesRowCount() throws Throwable {
        double[][] sixBySevenMatrix = new double[6][7];
        Covariance covariance = new Covariance(sixBySevenMatrix, false);
        assertEquals(6, covariance.getN());
    }

    // covariance(double[], double[]) requires at least 2 observations
    @Test(timeout = 4000)
    public void covarianceOfSingleElementArrays_throwsRuntimeException() throws Throwable {
        Covariance covariance = new Covariance();
        double[] singleElementArray = new double[1];
        try {
            covariance.covariance(singleElementArray, singleElementArray);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }

    // covariance(double[], double[]) requires both arrays to have equal length
    @Test(timeout = 4000)
    public void covarianceOfUnequalLengthArrays_throwsRuntimeException() throws Throwable {
        Covariance covariance = new Covariance();
        double[] twoElementArray = new double[2];
        double[] oneElementArray = new double[1];
        try {
            covariance.covariance(twoElementArray, oneElementArray);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.stat.correlation.Covariance", e);
        }
    }

    // covariance of identical constant arrays is zero (no variance)
    @Test(timeout = 4000)
    public void covarianceOfConstantArrays_returnsZero() throws Throwable {
        double[][] thirteenByOneMatrix = new double[13][1];
        double[] nineZeroArray = new double[9];
        Covariance covariance = new Covariance(thirteenByOneMatrix, false);
        double result = covariance.covariance(nineZeroArray, nineZeroArray);
        assertEquals(0.0, result, 0.01);
    }

    // Default constructor initializes N to 0
    @Test(timeout = 4000)
    public void defaultConstructor_initializesNToZero() throws Throwable {
        Covariance covariance = new Covariance();
        assertEquals(0, covariance.getN());
    }

    // computeCovarianceMatrix(RealMatrix) requires matrix to have at least 1 column
    @Test(timeout = 4000)
    public void computeCovarianceMatrix_withEmptyMatrix_throwsRuntimeException() throws Throwable {
        double[][] thirteenByOneMatrix = new double[13][1];
        Covariance covariance = new Covariance(thirteenByOneMatrix, false);
        Array2DRowRealMatrix emptyMatrix = new Array2DRowRealMatrix();
        try {
            covariance.computeCovarianceMatrix(emptyMatrix);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.apache.commons.math4.legacy.linear.AbstractRealMatrix", e);
        }
    }

    // computeCovarianceMatrix(double[][]) returns a square matrix
    @Test(timeout = 4000)
    public void computeCovarianceMatrix_fromDoubleArray_returnsSquareMatrix() throws Throwable {
        double[][] thirteenByOneMatrix = new double[13][1];
        Covariance covariance = new Covariance(thirteenByOneMatrix, false);
        RealMatrix covMatrix = covariance.computeCovarianceMatrix(thirteenByOneMatrix);
        assertTrue(covMatrix.isSquare());
    }

    // getCovarianceMatrix() on default-constructed Covariance returns null without error
    @Test(timeout = 4000)
    public void getCovarianceMatrix_onDefaultCovariance_doesNotThrow() throws Throwable {
        Covariance covariance = new Covariance();
        covariance.getCovarianceMatrix();
        assertEquals(0, covariance.getN());
    }

    // Constructor with bias-corrected (default true) 6-row matrix stores N=6
    @Test(timeout = 4000)
    public void constructorWithMatrix_biasCorrectedTrue_storesRowCount() throws Throwable {
        double[][] sixByOneMatrix = new double[6][1];
        Covariance covariance = new Covariance(sixByOneMatrix);
        assertEquals(6, covariance.getN());
    }

    // Constructor accepting a RealMatrix (DiagonalMatrix) stores N equal to matrix dimension
    @Test(timeout = 4000)
    public void constructorWithRealMatrix_storesRowCount() throws Throwable {
        double[] sixZeroArray = new double[6];
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix(sixZeroArray, false);
        Covariance covariance = new Covariance(diagonalMatrix);
        assertEquals(6, covariance.getN());
    }
}
