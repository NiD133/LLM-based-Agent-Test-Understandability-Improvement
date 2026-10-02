package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testParametricUsage3 {

    private final double EPS = Math.ulp(1d);

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage3() {
        final Sigmoid.Parametric g = new Sigmoid.Parametric();
        g.gradient(0, null);
    }
}
