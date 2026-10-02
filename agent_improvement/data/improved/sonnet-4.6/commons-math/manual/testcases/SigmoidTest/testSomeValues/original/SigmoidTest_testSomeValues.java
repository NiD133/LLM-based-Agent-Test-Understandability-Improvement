package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testSomeValues {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testSomeValues() {
        final UnivariateFunction f = new Sigmoid();
        Assert.assertEquals(0.5, f.value(0), EPS);
        Assert.assertEquals(0, f.value(Double.NEGATIVE_INFINITY), EPS);
        Assert.assertEquals(1, f.value(Double.POSITIVE_INFINITY), EPS);
    }
}
