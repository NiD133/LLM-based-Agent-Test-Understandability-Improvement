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
 *
 * <p>A {@link Gaussian} models the bell curve
 * {@code norm * exp(-(x - mean)^2 / (2 * sigma^2))}. The standard,
 * no-argument constructor uses {@code norm = 1 / sqrt(2 * PI)},
 * {@code mean = 0} and {@code sigma = 1}, which is the standard normal
 * probability density function.</p>
 */
public class GaussianTest {

    /** Tolerance for comparisons that should be exact up to rounding noise. */
    private final double EPS = Math.ulp(1d);

    /** Tolerance for comparisons that are expected to be exactly equal. */
    private static final double EXACT = 0;

    @Test(expected = NotStrictlyPositiveException.class)
    public void testPreconditions() {
        // The standard deviation (sigma) must be strictly positive.
        final double negativeSigma = -1;
        new Gaussian(1, 2, negativeSigma);
    }

    @Test
    public void testSomeValues() {
        // Standard normal density: peak value at x = 0 is 1 / sqrt(2 * PI).
        final UnivariateFunction standardNormal = new Gaussian();

        final double expectedPeak = 1 / JdkMath.sqrt(2 * Math.PI);
        Assert.assertEquals(expectedPeak, standardNormal.value(0), EPS);
    }

    @Test
    public void testLargeArguments() {
        // Far from the mean (in both directions) the density underflows to 0.
        final UnivariateFunction standardNormal = new Gaussian();

        Assert.assertEquals(0, standardNormal.value(Double.NEGATIVE_INFINITY), EXACT);
        Assert.assertEquals(0, standardNormal.value(-Double.MAX_VALUE), EXACT);
        Assert.assertEquals(0, standardNormal.value(-1e2), EXACT);
        Assert.assertEquals(0, standardNormal.value(1e2), EXACT);
        Assert.assertEquals(0, standardNormal.value(Double.MAX_VALUE), EXACT);
        Assert.assertEquals(0, standardNormal.value(Double.POSITIVE_INFINITY), EXACT);
    }

    @Test
    public void testDerivatives() {
        // Evaluate the Gaussian and its first four derivatives at x = 1.1
        // using automatic differentiation (DerivativeStructure).
        final UnivariateDifferentiableFunction gaussian = new Gaussian(2.0, 0.9, 3.0);

        final int variableCount = 1;
        final int derivativeOrder = 4;
        final int variableIndex = 0;
        final double x = 1.1;
        final DerivativeStructure dsX =
            new DerivativeStructure(variableCount, derivativeOrder, variableIndex, x);
        final DerivativeStructure dsY = gaussian.value(dsX);

        Assert.assertEquals( 1.9955604901712128349,   dsY.getValue(),              EPS);
        Assert.assertEquals(-0.044345788670471396332, dsY.getPartialDerivative(1), EPS);
        Assert.assertEquals(-0.22074348138190206174,  dsY.getPartialDerivative(2), EPS);
        Assert.assertEquals( 0.014760030401924800557, dsY.getPartialDerivative(3), EPS);
        Assert.assertEquals( 0.073253159785035691678, dsY.getPartialDerivative(4), EPS);
    }

    @Test
    public void testDerivativeLargeArguments() {
        // With a very small sigma the curve is extremely narrow, so the first
        // derivative is 0 for any argument away from the mean (including the
        // extreme magnitudes below).
        final Gaussian f = new Gaussian(0, 1e-50);

        final int variableCount = 1;
        final int derivativeOrder = 1;
        final int variableIndex = 0;

        final double[] arguments = {
            Double.NEGATIVE_INFINITY,
            -Double.MAX_VALUE,
            -1e50,
            -1e2,
            1e2,
            1e50,
            Double.MAX_VALUE,
            Double.POSITIVE_INFINITY
        };

        for (final double x : arguments) {
            final DerivativeStructure dsX =
                new DerivativeStructure(variableCount, derivativeOrder, variableIndex, x);
            Assert.assertEquals(0, f.value(dsX).getPartialDerivative(1), EXACT);
        }
    }

    @Test
    public void testDerivativesNaN() {
        // A NaN argument must propagate to the value and to every derivative.
        final Gaussian f = new Gaussian(0, 1e-50);

        final int variableCount = 1;
        final int derivativeOrder = 5;
        final int variableIndex = 0;
        final DerivativeStructure fx =
            f.value(new DerivativeStructure(variableCount, derivativeOrder, variableIndex, Double.NaN));

        for (int order = 0; order <= fx.getOrder(); ++order) {
            Assert.assertTrue(Double.isNaN(fx.getPartialDerivative(order)));
        }
    }

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage1() {
        // value() rejects a null parameter array.
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage2() {
        // value() requires exactly three parameters (norm, mean, sigma).
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, new double[] {0});
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricUsage3() {
        // value() rejects a non-positive sigma (the third parameter).
        final Gaussian.Parametric g = new Gaussian.Parametric();
        final double[] paramsWithZeroSigma = {0, 1, 0};
        g.value(0, paramsWithZeroSigma);
    }

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage4() {
        // gradient() rejects a null parameter array.
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsage5() {
        // gradient() requires exactly three parameters (norm, mean, sigma).
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, new double[] {0});
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricUsage6() {
        // gradient() rejects a non-positive sigma (the third parameter).
        final Gaussian.Parametric g = new Gaussian.Parametric();
        final double[] paramsWithZeroSigma = {0, 1, 0};
        g.gradient(0, paramsWithZeroSigma);
    }

    @Test
    public void testParametricValue() {
        // The Parametric form, given (norm, mean, sigma), must match a directly
        // constructed Gaussian with the same parameters.
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;
        final Gaussian f = new Gaussian(norm, mean, sigma);

        final Gaussian.Parametric g = new Gaussian.Parametric();
        final double[] params = {norm, mean, sigma};
        Assert.assertEquals(f.value(-1), g.value(-1, params), EXACT);
        Assert.assertEquals(f.value(0),  g.value(0, params),  EXACT);
        Assert.assertEquals(f.value(2),  g.value(2, params),  EXACT);
    }

    @Test
    public void testParametricGradient() {
        // The gradient is the vector of partial derivatives with respect to
        // each parameter: d/d(norm), d/d(mean) and d/d(sigma).
        final double norm = 2;
        final double mean = 3;
        final double sigma = 4;
        final Gaussian.Parametric f = new Gaussian.Parametric();

        final double x = 1;
        final double[] grad = f.gradient(x, new double[] {norm, mean, sigma});

        final double diff = x - mean;
        final double sigmaSquared = sigma * sigma;

        // Partial derivative with respect to norm.
        final double dNorm = JdkMath.exp(-diff * diff / (2 * sigmaSquared));
        Assert.assertEquals(dNorm, grad[0], EPS);

        // Partial derivative with respect to mean.
        final double dMean = norm * dNorm * diff / sigmaSquared;
        Assert.assertEquals(dMean, grad[1], EPS);

        // Partial derivative with respect to sigma.
        final double dSigma = dMean * diff / sigma;
        Assert.assertEquals(dSigma, grad[2], EPS);
    }
}
