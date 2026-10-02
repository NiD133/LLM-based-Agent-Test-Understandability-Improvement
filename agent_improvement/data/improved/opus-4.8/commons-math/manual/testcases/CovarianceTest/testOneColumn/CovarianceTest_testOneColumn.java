package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

public class CovarianceTest_testOneColumn {

    /** Tolerance for floating-point comparisons. */
    private static final double TOLERANCE = 1.0e-15;

    /**
     * The covariance of a single-column data set is a 1x1 matrix whose only
     * entry is the (population) variance of that column.
     *
     * <p>Here the column holds the values {1, 2}. With {@code biasCorrected=false}
     * the population variance is used:
     * mean = 1.5, so variance = ((1-1.5)^2 + (2-1.5)^2) / 2 = 0.25.</p>
     */
    @Test
    public void testOneColumn() {
        double[][] singleColumn = { { 1 }, { 2 } };
        boolean biasCorrected = false;

        RealMatrix covariance =
                new Covariance(singleColumn, biasCorrected).getCovarianceMatrix();

        Assert.assertEquals(1, covariance.getRowDimension());
        Assert.assertEquals(1, covariance.getColumnDimension());
        Assert.assertEquals(0.25, covariance.getEntry(0, 0), TOLERANCE);
    }
}
