package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

/**
 * Verifies that {@link Sigmoid.Parametric#gradient(double, double...)} rejects a
 * parameter array whose length does not match the two parameters (lower asymptote
 * and higher asymptote) that a sigmoid function expects.
 */
public class SigmoidTest_testParametricUsage4 {

    /**
     * A Sigmoid.Parametric requires exactly two parameters. Supplying a single
     * parameter should raise a DimensionMismatchException.
     */
    @Test(expected = DimensionMismatchException.class)
    public void gradientRejectsWrongNumberOfParameters() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        final double x = 0;
        final double[] tooFewParameters = { 0 };

        sigmoid.gradient(x, tooFewParameters);
    }
}
