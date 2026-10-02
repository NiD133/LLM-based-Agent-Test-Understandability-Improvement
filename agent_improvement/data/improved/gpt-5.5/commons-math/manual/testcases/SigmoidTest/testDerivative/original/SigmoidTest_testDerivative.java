package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivative {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testDerivative() {
        final Sigmoid f = new Sigmoid();
        final DerivativeStructure f0 = f.value(new DerivativeStructure(1, 1, 0, 0.0));
        Assert.assertEquals(0.25, f0.getPartialDerivative(1), 0);
    }
}
