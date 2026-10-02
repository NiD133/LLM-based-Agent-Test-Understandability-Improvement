package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that the derivative of the {@link Sigmoid} function vanishes for
 * arguments far from the origin.
 *
 * <p>A sigmoid saturates towards its asymptotes as the input grows in either
 * direction, so its slope (the first derivative) tends to zero. This test feeds
 * a range of large positive and negative inputs—including the extreme values
 * {@code ±Double.MAX_VALUE} and {@code ±Infinity}—and checks that the computed
 * derivative is exactly {@code 0}.</p>
 */
public class SigmoidTest_testDerivativeLargeArguments {

    /** Number of input variables the {@link DerivativeStructure} depends on. */
    private static final int VARIABLE_COUNT = 1;

    /** Highest derivative order to compute (1 = first derivative). */
    private static final int DERIVATIVE_ORDER = 1;

    /** Index of the single input variable. */
    private static final int VARIABLE_INDEX = 0;

    /** Tolerance for the assertion: the derivative must be exactly zero. */
    private static final double EXACT = 0;

    @Test
    public void testDerivativeLargeArguments() {
        // Sigmoid with lower asymptote 1 and upper asymptote 2.
        final Sigmoid sigmoid = new Sigmoid(1, 2);

        final double[] largeArguments = {
            Double.NEGATIVE_INFINITY,
            -Double.MAX_VALUE,
            -1e50,
            -1e3,
            1e3,
            1e50,
            Double.MAX_VALUE,
            Double.POSITIVE_INFINITY,
        };

        for (final double argument : largeArguments) {
            final DerivativeStructure input =
                new DerivativeStructure(VARIABLE_COUNT, DERIVATIVE_ORDER, VARIABLE_INDEX, argument);
            final double firstDerivative =
                sigmoid.value(input).getPartialDerivative(DERIVATIVE_ORDER);

            Assert.assertEquals("derivative should vanish at " + argument,
                                0, firstDerivative, EXACT);
        }
    }
}
