package org.apache.commons.math4.legacy.stat.correlation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Verifies that {@link Covariance} returns zero whenever at least one of the two
 * sample arrays has no variance (i.e. all of its values are identical).
 *
 * <p>Covariance measures how two variables vary together. If one variable never
 * changes, there is nothing to co-vary with, so the covariance must be zero.</p>
 */
public class CovarianceTest_testConstant {

    /** Tolerance for comparing the expected covariance (0.0) against the computed value. */
    private static final double TOLERANCE = Double.MIN_VALUE;

    /** {@code true} requests the bias-corrected (sample) covariance estimate. */
    private static final boolean BIAS_CORRECTED = true;

    @Test
    public void testConstant() {
        // A column whose values never change -> zero variance.
        final double[] constantColumn = { 1, 1, 1, 1 };
        // A column that does vary, used as the second operand.
        final double[] varyingColumn = { 1, 2, 3, 4 };

        // Covariance of a constant column with a varying column is zero.
        assertEquals(0d,
                new Covariance().covariance(constantColumn, varyingColumn, BIAS_CORRECTED),
                TOLERANCE);

        // Covariance of a constant column with itself is also zero.
        assertEquals(0d,
                new Covariance().covariance(constantColumn, constantColumn, BIAS_CORRECTED),
                TOLERANCE);
    }
}
