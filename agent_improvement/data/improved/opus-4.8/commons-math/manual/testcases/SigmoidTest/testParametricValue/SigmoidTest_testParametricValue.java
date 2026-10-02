package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Sigmoid.Parametric}, which receives the asymptotes as
 * runtime parameters, produces exactly the same value as a {@link Sigmoid}
 * configured with those same asymptotes.
 */
public class SigmoidTest_testParametricValue {

    /** Lower asymptote of the sigmoid. */
    private static final double LOWER_ASYMPTOTE = 2;
    /** Upper asymptote of the sigmoid. */
    private static final double UPPER_ASYMPTOTE = 3;

    @Test
    public void testParametricValue() {
        final Sigmoid configuredSigmoid = new Sigmoid(LOWER_ASYMPTOTE, UPPER_ASYMPTOTE);
        final Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();
        final double[] asymptotes = { LOWER_ASYMPTOTE, UPPER_ASYMPTOTE };

        // For every sample point the parametric form must match the configured form exactly.
        for (final double x : new double[] { -1, 0, 2 }) {
            Assert.assertEquals(configuredSigmoid.value(x),
                                parametricSigmoid.value(x, asymptotes),
                                0);
        }
    }
}
