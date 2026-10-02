package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Covariance} rejects inputs that do not contain enough
 * data to compute a covariance.
 */
public class CovarianceTest_testInsufficientData {

    /**
     * Covariance requires at least two observations. Both the pairwise
     * covariance method and the matrix-based constructor must reject inputs
     * that fall short of this requirement.
     */
    @Test
    public void testInsufficientData() {
        // A single observation per series is not enough to compute a covariance.
        final double[] singleValueX = { 1 };
        final double[] singleValueY = { 2 };
        try {
            new Covariance().covariance(singleValueX, singleValueY, false);
            Assert.fail("Expecting MathIllegalArgumentException for single-observation series");
        } catch (MathIllegalArgumentException ex) {
            // Expected: too few observations to compute a covariance.
        }

        // A matrix whose columns contain no observations is also insufficient.
        final double[][] emptyColumns = { {}, {} };
        try {
            new Covariance(emptyColumns);
            Assert.fail("Expecting NotStrictlyPositiveException for empty columns");
        } catch (NotStrictlyPositiveException ex) {
            // Expected: the number of observations must be strictly positive.
        }
    }
}
