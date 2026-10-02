package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

public class CovarianceTest_testOneColumn {

    private static final double ASSERTION_TOLERANCE = 1.0e-15;

    /**
     * A two-observation, one-variable data set should produce a 1x1 covariance
     * matrix containing the biased covariance for that single variable.
     */
    @Test
    public void testOneColumn() {
        RealMatrix covarianceMatrix = new Covariance(new double[][] { { 1 }, { 2 } }, false)
                .getCovarianceMatrix();

        Assert.assertEquals(1, covarianceMatrix.getRowDimension());
        Assert.assertEquals(1, covarianceMatrix.getColumnDimension());
        Assert.assertEquals(0.25, covarianceMatrix.getEntry(0, 0), ASSERTION_TOLERANCE);
    }
}
