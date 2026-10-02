package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivativesHighOrder {

    private final double EPS = Math.ulp(1d);

    @Test
    public void testDerivativesHighOrder() {
        DerivativeStructure s = new Sigmoid(1, 3).value(new DerivativeStructure(1, 5, 0, 1.2));
        Assert.assertEquals(2.5370495669980352859, s.getPartialDerivative(0), 5.0e-16);
        Assert.assertEquals(0.35578888129361140441, s.getPartialDerivative(1), 6.0e-17);
        Assert.assertEquals(-0.19107626464144938116, s.getPartialDerivative(2), 6.0e-17);
        Assert.assertEquals(-0.02396830286286711696, s.getPartialDerivative(3), 4.0e-17);
        Assert.assertEquals(0.21682059798981049049, s.getPartialDerivative(4), 3.0e-17);
        Assert.assertEquals(-0.19186320234632658055, s.getPartialDerivative(5), 2.0e-16);
    }
}
