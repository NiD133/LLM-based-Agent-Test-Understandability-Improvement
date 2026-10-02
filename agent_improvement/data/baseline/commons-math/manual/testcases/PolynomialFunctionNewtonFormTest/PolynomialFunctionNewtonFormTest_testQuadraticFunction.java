package org.apache.commons.math4.legacy.analysis.polynomials;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class PolynomialFunctionNewtonFormTest_testQuadraticFunction {

    /**
     * Test of polynomial for the quadratic function.
     */
    @Test
    public void testQuadraticFunction() {
        PolynomialFunctionNewtonForm p;
        double[] coefficients;
        double z;
        double expected;
        double result;
        double tolerance = 1E-12;
        // p(x) = 2x^2 + 5x - 3 = 4 + 3(x-1) + 2(x-1)(x+2)
        double[] a = { 4.0, 3.0, 2.0 };
        double[] c = { 1.0, -2.0 };
        p = new PolynomialFunctionNewtonForm(a, c);
        z = 1.0;
        expected = 4.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        z = 2.5;
        expected = 22.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        z = -2.0;
        expected = -5.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        Assert.assertEquals(2, p.degree());
        coefficients = p.getCoefficients();
        Assert.assertEquals(3, coefficients.length);
        Assert.assertEquals(-3.0, coefficients[0], tolerance);
        Assert.assertEquals(5.0, coefficients[1], tolerance);
        Assert.assertEquals(2.0, coefficients[2], tolerance);
    }
}
