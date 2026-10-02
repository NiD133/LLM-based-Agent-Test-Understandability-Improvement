package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testParametricValue {

    @Test
    public void testParametricValue() {
        // Sigmoid bounded between lo=2 (lower asymptote) and hi=3 (upper asymptote)
        final double lowerAsymptote = 2;
        final double upperAsymptote = 3;

        // Direct sigmoid instance with explicit bounds
        final Sigmoid boundedSigmoid = new Sigmoid(lowerAsymptote, upperAsymptote);

        // Parametric form accepts the same bounds as an array argument
        final Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();
        final double[] params = new double[] { lowerAsymptote, upperAsymptote };

        // Both forms must agree at three representative inputs:
        // negative input, zero, and a positive input
        Assert.assertEquals(
                boundedSigmoid.value(-1),
                parametricSigmoid.value(-1, params),
                0);
        Assert.assertEquals(
                boundedSigmoid.value(0),
                parametricSigmoid.value(0, params),
                0);
        Assert.assertEquals(
                boundedSigmoid.value(2),
                parametricSigmoid.value(2, params),
                0);
    }
}
