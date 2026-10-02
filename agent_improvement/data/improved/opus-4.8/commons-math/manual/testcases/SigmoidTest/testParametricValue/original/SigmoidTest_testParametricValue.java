package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testParametricValue {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testParametricValue() {
        final double lo = 2;
        final double hi = 3;
        final Sigmoid f = new Sigmoid(lo, hi);
        final Sigmoid.Parametric g = new Sigmoid.Parametric();
        Assert.assertEquals(f.value(-1), g.value(-1, new double[] { lo, hi }), 0);
        Assert.assertEquals(f.value(0), g.value(0, new double[] { lo, hi }), 0);
        Assert.assertEquals(f.value(2), g.value(2, new double[] { lo, hi }), 0);
    }
}
