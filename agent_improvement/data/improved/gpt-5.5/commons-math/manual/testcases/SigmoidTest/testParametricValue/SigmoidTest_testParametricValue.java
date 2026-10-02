package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testParametricValue {

    private static final double LOWER_ASYMPTOTE = 2;
    private static final double UPPER_ASYMPTOTE = 3;
    private static final double EXACT_MATCH = 0;

    @Test
    public void testParametricValue() {
        final Sigmoid sigmoid = new Sigmoid(LOWER_ASYMPTOTE, UPPER_ASYMPTOTE);
        final Sigmoid.Parametric parametricSigmoid = new Sigmoid.Parametric();

        assertParametricValueMatchesSigmoid(sigmoid, parametricSigmoid, -1);
        assertParametricValueMatchesSigmoid(sigmoid, parametricSigmoid, 0);
        assertParametricValueMatchesSigmoid(sigmoid, parametricSigmoid, 2);
    }

    private void assertParametricValueMatchesSigmoid(final Sigmoid sigmoid,
                                                    final Sigmoid.Parametric parametricSigmoid,
                                                    final double x) {
        Assert.assertEquals(sigmoid.value(x),
                            parametricSigmoid.value(x, new double[] { LOWER_ASYMPTOTE, UPPER_ASYMPTOTE }),
                            EXACT_MATCH);
    }
}
