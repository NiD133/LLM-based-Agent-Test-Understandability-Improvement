package org.apache.commons.math4.legacy.analysis.polynomials;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class PolynomialFunctionNewtonFormTest_testDerivative {

    /**
     * Test for derivatives.
     */
    @Test
    public void testDerivative() {
        // x^3 = 0 * [1] + 1 * [x] + 3 * [x(x-1)] + 1 * [x(x-1)(x-2)]
        PolynomialFunctionNewtonForm p = new PolynomialFunctionNewtonForm(new double[] { 0, 1, 3, 1 }, new double[] { 0, 1, 2 });
        double eps = 2.0e-14;
        for (double t = 0.0; t < 10.0; t += 0.1) {
            DerivativeStructure x = new DerivativeStructure(1, 4, 0, t);
            DerivativeStructure y = p.value(x);
            Assert.assertEquals(t * t * t, y.getValue(), eps * t * t * t);
            Assert.assertEquals(3.0 * t * t, y.getPartialDerivative(1), eps * 3.0 * t * t);
            Assert.assertEquals(6.0 * t, y.getPartialDerivative(2), eps * 6.0 * t);
            Assert.assertEquals(6.0, y.getPartialDerivative(3), eps * 6.0);
            Assert.assertEquals(0.0, y.getPartialDerivative(4), eps);
        }
    }
}
