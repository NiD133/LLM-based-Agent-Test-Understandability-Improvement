package org.apache.commons.math4.legacy.analysis.polynomials;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.exception.MathIllegalArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class PolynomialFunctionNewtonFormTest_testParameters {

    /**
     * Test of parameters for the polynomial.
     */
    @Test
    public void testParameters() {
        try {
            // bad input array length
            double[] a = { 1.0 };
            double[] c = { 2.0 };
            new PolynomialFunctionNewtonForm(a, c);
            Assert.fail("Expecting MathIllegalArgumentException - bad input array length");
        } catch (MathIllegalArgumentException ex) {
            // expected
        }
        try {
            // mismatch input arrays
            double[] a = { 1.0, 2.0, 3.0, 4.0 };
            double[] c = { 4.0, 3.0, 2.0, 1.0 };
            new PolynomialFunctionNewtonForm(a, c);
            Assert.fail("Expecting MathIllegalArgumentException - mismatch input arrays");
        } catch (MathIllegalArgumentException ex) {
            // expected
        }
    }
}
