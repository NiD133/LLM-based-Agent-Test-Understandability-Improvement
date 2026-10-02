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

import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.legacy.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for class {@link Gaussian}.
 */
public class GaussianTest {
    private static final double EPS = Math.ulp(1d);

    private static final double PARAMETRIC_NORM = 2;
    private static final double PARAMETRIC_MEAN = 3;
    private static final double PARAMETRIC_SIGMA = 4;

    @Test(expected=NotStrictlyPositiveException.class)
    public void testPreconditions() {
        new Gaussian(1, 2, -1);
    }

    @Test
    public void testSomeValues() {
        final UnivariateFunction f = new Gaussian();

        Assert.assertEquals(1 / JdkMath.sqrt(2 * Math.PI), f.value(0), EPS);
    }

    @Test
    public void testLargeArguments() {
        final UnivariateFunction f = new Gaussian();

        Assert.assertEquals(0, f.value(Double.NEGATIVE_INFINITY), 0);
        Assert.assertEquals(0, f.value(-Double.MAX_VALUE), 0);
        Assert.assertEquals(0, f.value(-1e2), 0);
        Assert.assertEquals(0, f.value(1e2), 0);
        Assert.assertEquals(0, f.value(Double.MAX_VALUE), 0);
        Assert.assertEquals(0, f.value(Double.POSITIVE_INFINITY), 0);
    }

    @Test
    public void testDerivatives() {
        final UnivariateDifferentiableFunction gaussian = new Gaussian(2.0, 0.9, 3.0);
        final DerivativeStructure dsX = new DerivativeStructure(1, 4, 0, 1.1);
        final DerivativeStructure dsY = gaussian.value(dsX);

        Assert.assertEquals( 1.9955604901712128349,   dsY.getValue(),              EPS);
        Assert.assertEquals(-0.044345788670471396332, dsY.getPartialDerivative(1), EPS);
        Assert.assertEquals(-0.22074348138190206174,  dsY.getPartialDerivative(2), EPS);
        Assert.assertEquals( 0.014760030401924800557, dsY.getPartialDerivative(3), EPS);
        Assert.assertEquals( 0.073253159785035691678, dsY.getPartialDerivative(4), EPS);
    }

    @Test
    public void testDerivativeLargeArguments() {
        final Gaussian f = new Gaussian(0, 1e-50);

        Assert.assertEquals(0, firstDerivativeAt(f, Double.NEGATIVE_INFINITY), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, -Double.MAX_VALUE), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, -1e50), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, -1e2), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, 1e2), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, 1e50), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, Double.MAX_VALUE), 0);
        Assert.assertEquals(0, firstDerivativeAt(f, Double.POSITIVE_INFINITY), 0);
    }

    @Test
    public void testDerivativesNaN() {
        final Gaussian f = new Gaussian(0, 1e-50);
        final DerivativeStructure fx = f.value(new DerivativeStructure(1, 5, 0, Double.NaN));

        for (int i = 0; i <= fx.getOrder(); ++i) {
            Assert.assertTrue(Double.isNaN(fx.getPartialDerivative(i)));
        }
    }

    @Test(expected=NullArgumentException.class)
    public void testParametricUsage1() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, null);
    }

    @Test(expected=DimensionMismatchException.class)
    public void testParametricUsage2() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, new double[] {0});
    }

    @Test(expected=NotStrictlyPositiveException.class)
    public void testParametricUsage3() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.value(0, new double[] {0, 1, 0});
    }

    @Test(expected=NullArgumentException.class)
    public void testParametricUsage4() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, null);
    }

    @Test(expected=DimensionMismatchException.class)
    public void testParametricUsage5() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, new double[] {0});
    }

    @Test(expected=NotStrictlyPositiveException.class)
    public void testParametricUsage6() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, new double[] {0, 1, 0});
    }

    @Test
    public void testParametricValue() {
        final Gaussian f = new Gaussian(PARAMETRIC_NORM, PARAMETRIC_MEAN, PARAMETRIC_SIGMA);
        final Gaussian.Parametric g = new Gaussian.Parametric();

        Assert.assertEquals(f.value(-1), g.value(-1, parametricArguments()), 0);
        Assert.assertEquals(f.value(0), g.value(0, parametricArguments()), 0);
        Assert.assertEquals(f.value(2), g.value(2, parametricArguments()), 0);
    }

    @Test
    public void testParametricGradient() {
        final Gaussian.Parametric f = new Gaussian.Parametric();

        final double x = 1;
        final double[] grad = f.gradient(1, parametricArguments());
        final double diff = x - PARAMETRIC_MEAN;
        final double n = JdkMath.exp(-diff * diff / (2 * PARAMETRIC_SIGMA * PARAMETRIC_SIGMA));
        Assert.assertEquals(n, grad[0], EPS);
        final double m = PARAMETRIC_NORM * n * diff / (PARAMETRIC_SIGMA * PARAMETRIC_SIGMA);
        Assert.assertEquals(m, grad[1], EPS);
        final double s = m * diff / PARAMETRIC_SIGMA;
        Assert.assertEquals(s, grad[2], EPS);
    }

    private static double firstDerivativeAt(final Gaussian f, final double x) {
        return f.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1);
    }

    private static double[] parametricArguments() {
        return new double[] {PARAMETRIC_NORM, PARAMETRIC_MEAN, PARAMETRIC_SIGMA};
    }
}
