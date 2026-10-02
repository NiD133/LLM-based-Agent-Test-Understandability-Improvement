package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

/**
 * Verifies the argument preconditions enforced by the {@link Gaussian} constructor.
 *
 * <p>A Gaussian is defined by {@code Gaussian(norm, mean, sigma)}. The standard
 * deviation {@code sigma} must be strictly positive; supplying a non-positive value
 * is expected to be rejected at construction time.</p>
 */
public class GaussianTest_testPreconditions {

    /** Standard deviation must be &gt; 0, so a negative value is invalid. */
    private static final double INVALID_SIGMA = -1;

    private static final double NORM = 1;
    private static final double MEAN = 2;

    @Test(expected = NotStrictlyPositiveException.class)
    public void constructorRejectsNonPositiveSigma() {
        new Gaussian(NORM, MEAN, INVALID_SIGMA);
    }
}
