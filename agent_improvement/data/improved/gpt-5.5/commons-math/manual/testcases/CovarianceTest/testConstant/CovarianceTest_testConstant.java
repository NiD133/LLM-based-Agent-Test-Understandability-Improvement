package org.apache.commons.math4.legacy.stat.correlation;

import org.junit.Assert;
import org.junit.Test;

public class CovarianceTest_testConstant {

    /**
     * A data set with no variance has zero covariance with any compatible data
     * set, including itself.
     */
    @Test
    public void testConstant() {
        final double[] constantValues = new double[] { 1, 1, 1, 1 };
        final double[] increasingValues = new double[] { 1, 2, 3, 4 };
        final Covariance covariance = new Covariance();

        Assert.assertEquals(0d, covariance.covariance(constantValues, increasingValues, true), Double.MIN_VALUE);
        Assert.assertEquals(0d, covariance.covariance(constantValues, constantValues, true), Double.MIN_VALUE);
    }
}
