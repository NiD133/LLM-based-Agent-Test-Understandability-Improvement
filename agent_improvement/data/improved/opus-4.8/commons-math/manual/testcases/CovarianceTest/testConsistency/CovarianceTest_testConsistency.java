package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.TestUtils;
import org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.apache.commons.statistics.descriptive.Variance;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that a {@link Covariance} matrix is internally consistent:
 * its diagonal holds the per-column variances, it is symmetric, and the
 * various ways of asking for a covariance all agree with each other.
 */
public class CovarianceTest_testConsistency {

    /** Tolerance for floating-point comparisons of covariance/variance values. */
    private static final double TOLERANCE = 10E-14;

    /** Tolerance used when two values are expected to be bit-for-bit identical. */
    private static final double EXACT = Double.MIN_VALUE;

    /** Number of observations (rows) in {@link #SWISS_DATA}. */
    private static final int SWISS_ROWS = 47;

    /** Number of variables (columns) in {@link #SWISS_DATA}. */
    private static final int SWISS_COLS = 5;

    /**
     * Swiss fertility data, stored row-major: each block of {@value #SWISS_COLS}
     * values is one observation.
     */
    private static final double[] SWISS_DATA = new double[] { 80.2, 17.0, 15, 12, 9.96, 83.1, 45.1, 6, 9, 84.84, 92.5, 39.7, 5, 5, 93.40, 85.8, 36.5, 12, 7, 33.77, 76.9, 43.5, 17, 15, 5.16, 76.1, 35.3, 9, 7, 90.57, 83.8, 70.2, 16, 7, 92.85, 92.4, 67.8, 14, 8, 97.16, 82.4, 53.3, 12, 7, 97.67, 82.9, 45.2, 16, 13, 91.38, 87.1, 64.5, 14, 6, 98.61, 64.1, 62.0, 21, 12, 8.52, 66.9, 67.5, 14, 7, 2.27, 68.9, 60.7, 19, 12, 4.43, 61.7, 69.3, 22, 5, 2.82, 68.3, 72.6, 18, 2, 24.20, 71.7, 34.0, 17, 8, 3.30, 55.7, 19.4, 26, 28, 12.11, 54.3, 15.2, 31, 20, 2.15, 65.1, 73.0, 19, 9, 2.84, 65.5, 59.8, 22, 10, 5.23, 65.0, 55.1, 14, 3, 4.52, 56.6, 50.9, 22, 12, 15.14, 57.4, 54.1, 20, 6, 4.20, 72.5, 71.2, 12, 1, 2.40, 74.2, 58.1, 14, 8, 5.23, 72.0, 63.5, 6, 3, 2.56, 60.5, 60.8, 16, 10, 7.72, 58.3, 26.8, 25, 19, 18.46, 65.4, 49.5, 15, 8, 6.10, 75.5, 85.9, 3, 2, 99.71, 69.3, 84.9, 7, 6, 99.68, 77.3, 89.7, 5, 2, 100.00, 70.5, 78.2, 12, 6, 98.96, 79.4, 64.9, 7, 3, 98.22, 65.0, 75.9, 9, 9, 99.06, 92.2, 84.6, 3, 3, 99.46, 79.3, 63.1, 13, 13, 96.83, 70.4, 38.4, 26, 12, 5.62, 65.7, 7.7, 29, 11, 13.79, 72.7, 16.7, 22, 13, 11.22, 64.4, 17.6, 35, 32, 16.92, 77.6, 37.6, 15, 7, 4.97, 67.6, 18.7, 25, 7, 8.65, 35.0, 1.2, 37, 53, 42.34, 44.7, 46.6, 16, 29, 50.43, 42.8, 27.7, 22, 29, 58.33 };

    /**
     * Reshapes a flat, row-major array into a {@code nRows x nCols} matrix.
     */
    private RealMatrix createRealMatrix(double[] data, int nRows, int nCols) {
        double[][] matrixData = new double[nRows][nCols];
        int ptr = 0;
        for (int i = 0; i < nRows; i++) {
            System.arraycopy(data, ptr, matrixData[i], 0, nCols);
            ptr += nCols;
        }
        return new Array2DRowRealMatrix(matrixData);
    }

    /**
     * Verify that diagonal entries are consistent with Variance computation and that
     * the matrix matches column-by-column covariances.
     */
    @Test
    public void testConsistency() {
        final RealMatrix matrix = createRealMatrix(SWISS_DATA, SWISS_ROWS, SWISS_COLS);
        final RealMatrix covarianceMatrix = new Covariance(matrix).getCovarianceMatrix();

        // The diagonal entry (i, i) must equal the variance of column i.
        for (int i = 0; i < SWISS_COLS; i++) {
            double expectedColumnVariance = Variance.of(matrix.getColumn(i)).getAsDouble();
            Assert.assertEquals(expectedColumnVariance, covarianceMatrix.getEntry(i, i), TOLERANCE);
        }

        // An off-diagonal entry must match the pairwise covariance of the two columns...
        double covarianceOfColumns2And3 =
            new Covariance().covariance(matrix.getColumn(2), matrix.getColumn(3), true);
        Assert.assertEquals(covarianceOfColumns2And3, covarianceMatrix.getEntry(2, 3), TOLERANCE);
        // ...and the matrix must be symmetric.
        Assert.assertEquals(covarianceMatrix.getEntry(2, 3), covarianceMatrix.getEntry(3, 2), EXACT);

        // If every column is identical, every covariance entry must equal that column's variance.
        final int repeatedColumnCount = 3;
        RealMatrix repeatedColumns = new Array2DRowRealMatrix(SWISS_ROWS, repeatedColumnCount);
        for (int i = 0; i < repeatedColumnCount; i++) {
            repeatedColumns.setColumnMatrix(i, matrix.getColumnMatrix(0));
        }
        RealMatrix repeatedCovarianceMatrix = new Covariance(repeatedColumns).getCovarianceMatrix();
        double firstColumnVariance = Variance.of(matrix.getColumn(0)).getAsDouble();
        for (int i = 0; i < repeatedColumnCount; i++) {
            for (int j = 0; j < repeatedColumnCount; j++) {
                Assert.assertEquals(firstColumnVariance, repeatedCovarianceMatrix.getEntry(i, j), TOLERANCE);
            }
        }

        // Bias correction is on by default, so the default matrix must equal the
        // explicitly bias-corrected one computed from the raw data.
        double[][] data = matrix.getData();
        TestUtils.assertEquals("Covariances", covarianceMatrix,
            new Covariance().computeCovarianceMatrix(data), EXACT);
        TestUtils.assertEquals("Covariances", covarianceMatrix,
            new Covariance().computeCovarianceMatrix(data, true), EXACT);

        // Likewise, the default pairwise covariance must equal the bias-corrected one.
        double[] x = data[0];
        double[] y = data[1];
        Assert.assertEquals(new Covariance().covariance(x, y), new Covariance().covariance(x, y, true), EXACT);
    }
}
