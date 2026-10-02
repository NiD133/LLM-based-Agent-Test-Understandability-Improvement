package org.apache.commons.math4.legacy.analysis.polynomials;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class PolynomialFunctionNewtonFormTest_testLinearFunction {

    /**
     * Test of polynomial for the linear function.
     */
    @Test
    public void testLinearFunction() {
        PolynomialFunctionNewtonForm p;
        double[] coefficients;
        double z;
        double expected;
        double result;
        double tolerance = 1E-12;
        // p(x) = 1.5x - 4 = 2 + 1.5(x-4)
        double[] a = { 2.0, 1.5 };
        double[] c = { 4.0 };
        p = new PolynomialFunctionNewtonForm(a, c);
        z = 2.0;
        expected = -1.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        z = 4.5;
        expected = 2.75;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        z = 6.0;
        expected = 5.0;
        result = p.value(z);
        Assert.assertEquals(expected, result, tolerance);
        Assert.assertEquals(1, p.degree());
        coefficients = p.getCoefficients();
        Assert.assertEquals(2, coefficients.length);
        Assert.assertEquals(-4.0, coefficients[0], tolerance);
        Assert.assertEquals(1.5, coefficients[1], tolerance);
    }
}
