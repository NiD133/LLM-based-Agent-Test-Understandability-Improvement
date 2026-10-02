package org.apache.commons.math4.legacy.stat.correlation;

import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that {@link Covariance} throws the correct exceptions when given
 * data that is too small to compute a covariance (fewer than 2 observations).
 */
public class CovarianceTest_testInsufficientData {

    /**
     * A single-element array cannot produce a covariance: covariance requires
     * at least 2 observations to compute a variance/covariance.
     */
    @Test
    public void testInsufficientData() {
        double[] singleElementArray1 = new double[] { 1 };
        double[] singleElementArray2 = new double[] { 2 };

        // Covariance of two length-1 arrays is undefined — must throw MathIllegalArgumentException
        try {
            new Covariance().covariance(singleElementArray1, singleElementArray2, false);
            Assert.fail("Expected MathIllegalArgumentException when input arrays have only one element");
        } catch (MathIllegalArgumentException ex) {
            // Expected: not enough data points
        }

        // A matrix with zero-length rows also has insufficient data — must throw NotStrictlyPositiveException
        try {
            new Covariance(new double[][] { {}, {} });
            Assert.fail("Expected NotStrictlyPositiveException when matrix rows have zero length");
        } catch (NotStrictlyPositiveException ex) {
            // Expected: number of columns must be strictly positive
        }
    }
}
