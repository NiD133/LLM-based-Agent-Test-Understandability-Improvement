/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for class {@link Gaussian}.
 */
public class GaussianTest {
    /** Tolerance equal to one unit in the last place of 1.0, used for floating-point comparisons. */
    private final double MACHINE_EPSILON = Math.ulp(1d);

    // -------------------------------------------------------------------------
    // Constructor validation
    // -------------------------------------------------------------------------

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorThrowsWhenSigmaIsNegative() {
        new Gaussian(1, 2, -1);
    }

    // -------------------------------------------------------------------------
    // Standard Gaussian (norm=1, mean=0, sigma=1) value checks
    // -------------------------------------------------------------------------

    @Test
    public void testDefaultGaussianPeakValueAtMean() {
        final UnivariateFunction f = new Gaussian();
        // At x=0 (the mean), the standard Gaussian equals 1/sqrt(2*pi).
        Assert.assertEquals(1 / JdkMath.sqrt(2 * Math.PI), f.value(0), MACHINE_EPSILON);
    }

    @Test
    public void testDefaultGaussianVanishesAtExtremeArguments() {
        final UnivariateFunction f = new Gaussian();

        Assert.assertEquals(0, f.value(Double.NEGATIVE_INFINITY), 0);
        Assert.assertEquals(0, f.value(-Double.MAX_VALUE),         0);
        Assert.assertEquals(0, f.value(-1e2),                      0);
        Assert.assertEquals(0, f.value(1e2),                       0);
        Assert.assertEquals(0, f.value(Double.MAX_VALUE),          0);
        Assert.assertEquals(0, f.value(Double.POSITIVE_INFINITY),  0);
    }

    // -------------------------------------------------------------------------
    // Automatic differentiation via DerivativeStructure
    // -------------------------------------------------------------------------

    @Test
    public void testDerivativesUpToFourthOrderMatchExpectedValues() {
        // Gaussian with norm=2.0, mean=0.9, sigma=3.0 evaluated at x=1.1
        final UnivariateDifferentiableFunction gaussian = new Gaussian(2.0, 0.9, 3.0);
        final DerivativeStructure x = new DerivativeStructure(1, 4, 0, 1.1);
        final DerivativeStructure y = gaussian.value(x);

        Assert.assertEquals( 1.9955604901712128349,   y.getValue(),              MACHINE_EPSILON);
        Assert.assertEquals(-0.044345788670471396332, y.getPartialDerivative(1), MACHINE_EPSILON);
        Assert.assertEquals(-0.22074348138190206174,  y.getPartialDerivative(2), MACHINE_EPSILON);
        Assert.assertEquals( 0.014760030401924800557, y.getPartialDerivative(3), MACHINE_EPSILON);
        Assert.assertEquals( 0.073253159785035691678, y.getPartialDerivative(4), MACHINE_EPSILON);
    }

    @Test
    public void testFirstDerivativeVanishesAtExtremeArguments() {
        // Very small sigma forces rapid decay; the derivative must be 0 at extreme x.
        final Gaussian f = new Gaussian(0, 1e-50);

        assertFirstDerivativeIsZero(f, Double.NEGATIVE_INFINITY);
        assertFirstDerivativeIsZero(f, -Double.MAX_VALUE);
        assertFirstDerivativeIsZero(f, -1e50);
        assertFirstDerivativeIsZero(f, -1e2);
        assertFirstDerivativeIsZero(f,  1e2);
        assertFirstDerivativeIsZero(f,  1e50);
        assertFirstDerivativeIsZero(f,  Double.MAX_VALUE);
        assertFirstDerivativeIsZero(f,  Double.POSITIVE_INFINITY);
    }

    @Test
    public void testAllDerivativesAreNaNWhenArgumentIsNaN() {
        final Gaussian f = new Gaussian(0, 1e-50);
        final DerivativeStructure fx = f.value(new DerivativeStructure(1, 5, 0, Double.NaN));

        for (int order = 0; order <= fx.getOrder(); ++order) {
            Assert.assertTrue(Double.isNaN(fx.getPartialDerivative(order)));
        }
    }

    // -------------------------------------------------------------------------
    // Gaussian.Parametric — input-validation guards
    // -------------------------------------------------------------------------

    @Test(expected = NullArgumentException.class)
    public void testParametricValueThrowsNullArgumentExceptionWhenParamsIsNull() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricValueThrowsDimensionMismatchWhenParamCountIsWrong() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, new double[] {0});
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricValueThrowsWhenSigmaIsNonPositive() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, new double[] {0, 1, 0});
    }

    @Test(expected = NullArgumentException.class)
    public void testParametricGradientThrowsNullArgumentExceptionWhenParamsIsNull() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricGradientThrowsDimensionMismatchWhenParamCountIsWrong() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, new double[] {0});
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricGradientThrowsWhenSigmaIsNonPositive() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, new double[] {0, 1, 0});
    }

    // -------------------------------------------------------------------------
    // Gaussian.Parametric — correctness checks
    // -------------------------------------------------------------------------

    @Test
    public void testParametricValueMatchesDirectGaussianValue() {
        final double norm  = 2;
        final double mean  = 3;
        final double sigma = 4;
        final Gaussian f = new Gaussian(norm, mean, sigma);
        final Gaussian.Parametric g = new Gaussian.Parametric();

        Assert.assertEquals(f.value(-1), g.value(-1, new double[] {norm, mean, sigma}), 0);
        Assert.assertEquals(f.value( 0), g.value( 0, new double[] {norm, mean, sigma}), 0);
        Assert.assertEquals(f.value( 2), g.value( 2, new double[] {norm, mean, sigma}), 0);
    }

    @Test
    public void testParametricGradientMatchesAnalyticalFormulas() {
        final double norm  = 2;
        final double mean  = 3;
        final double sigma = 4;
        final Gaussian.Parametric f = new Gaussian.Parametric();

        final double x    = 1;
        final double[] grad = f.gradient(1, new double[] {norm, mean, sigma});

        // Analytical partial derivatives of norm * exp(-(x-mean)^2 / (2*sigma^2))
        final double diff              = x - mean;
        final double expTerm           = JdkMath.exp(-diff * diff / (2 * sigma * sigma));
        final double dNorm             = expTerm;
        final double dMean             = norm * expTerm * diff / (sigma * sigma);
        final double dSigma            = dMean * diff / sigma;

        Assert.assertEquals(dNorm,  grad[0], MACHINE_EPSILON);
        Assert.assertEquals(dMean,  grad[1], MACHINE_EPSILON);
        Assert.assertEquals(dSigma, grad[2], MACHINE_EPSILON);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Asserts that the first-order partial derivative of {@code f} at the given
     * {@code x} value is exactly 0 (with zero tolerance).
     */
    private void assertFirstDerivativeIsZero(Gaussian f, double x) {
        final DerivativeStructure ds = new DerivativeStructure(1, 1, 0, x);
        Assert.assertEquals(0, f.value(ds).getPartialDerivative(1), 0);
    }
}
