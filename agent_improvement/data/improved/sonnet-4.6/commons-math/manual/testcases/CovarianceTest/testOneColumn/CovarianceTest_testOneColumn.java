package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

public class CovarianceTest_testOneColumn {

    /**
     * Verifies that computing covariance of a single-column matrix returns a 1x1 matrix
     * with the correct variance value (using biased estimator since biasCorrected=false).
     *
     * Input: two observations [1] and [2] with one variable.
     * The biased variance = mean((x - mean)^2) = ((1-1.5)^2 + (2-1.5)^2) / 2 = 0.25
     */
    @Test
    public void testOneColumn() {
        double[][] singleColumnData = { { 1 }, { 2 } };
        boolean biasCorrected = false;

        RealMatrix covarianceMatrix = new Covariance(singleColumnData, biasCorrected).getCovarianceMatrix();

        int expectedRows = 1;
        int expectedCols = 1;
        double expectedVariance = 0.25;
        double tolerance = 1.0e-15;

        Assert.assertEquals(expectedRows, covarianceMatrix.getRowDimension());
        Assert.assertEquals(expectedCols, covarianceMatrix.getColumnDimension());
        Assert.assertEquals(expectedVariance, covarianceMatrix.getEntry(0, 0), tolerance);
    }
}
