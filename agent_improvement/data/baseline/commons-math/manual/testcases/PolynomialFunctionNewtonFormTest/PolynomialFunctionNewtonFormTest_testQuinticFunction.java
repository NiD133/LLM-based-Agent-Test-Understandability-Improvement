package org.apache.commons.math4.legacy.analysis.polynomials;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class PolynomialFunctionNewtonFormTest_testQuinticFunction {

    /**
     * Test of polynomial for the quintic function.
     */
    @Test
    public void testQuinticFunction() {
        PolynomialFunctionNewtonForm p;
        double[] coefficients;
        double z;
        double expected;
        double result;
        double tolerance = 1E-12;
        // p(x) = x^5 - x^4 - 7x^3 + x^2 + 6x
        //      = 6x - 6x^2 -6x^2(x-1) + x^2(x-1)(x+1) + x^2(x-1)(x+1)(x-2)
        double[] a = { 0.0, 6.0, -6.0, -6.0, 1.0, 1.0 };
        double[] c = { 0.0, 0.0, 1.0, -1.0, 2.0 };
        p = new PolynomialFunctionNewtonForm(a, c);
        z = 0.0;
        expected = 0.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        z = -2.0;
        expected = 0.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        z = 4.0;
        expected = 360.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        Assert.assertEquals(5, p.degree());
        coefficients = p.getCoefficients();
        Assert.assertEquals(6, coefficients.length);
        Assert.assertEquals(0.0, coefficients[0], tolerance);
        Assert.assertEquals(6.0, coefficients[1], tolerance);
        Assert.assertEquals(1.0, coefficients[2], tolerance);
        Assert.assertEquals(-7.0, coefficients[3], tolerance);
        Assert.assertEquals(-1.0, coefficients[4], tolerance);
        Assert.assertEquals(1.0, coefficients[5], tolerance);
    }
}
