package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link Sigmoid.Parametric#gradient(double, double[])} rejects a
 * parameter array whose length does not match the two parameters (lower and
 * upper asymptote) that the sigmoid function expects.
 */
public class SigmoidTest_testParametricUsage4 {

    /**
     * A sigmoid is parameterised by exactly two values, so supplying a single
     * parameter must trigger a {@link DimensionMismatchException}.
     */
    @Test(expected = DimensionMismatchException.class)
    public void gradientRejectsWrongNumberOfParameters() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        final double evaluationPoint = 0;
        final double[] tooFewParameters = { 0 };

        sigmoid.gradient(evaluationPoint, tooFewParameters);
    }
}
