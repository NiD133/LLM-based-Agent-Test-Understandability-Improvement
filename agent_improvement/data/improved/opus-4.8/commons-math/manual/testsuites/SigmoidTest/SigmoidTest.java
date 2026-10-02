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
import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for class {@link Sigmoid}.
 */
public class SigmoidTest {

    /** Tolerance of one unit in the last place: results must match to full double precision. */
    private static final double ONE_ULP = Math.ulp(1d);

    /** Tolerance for assertions that must match the expected value exactly. */
    private static final double EXACT = 0;

    @Test
    public void testSomeValues() {
        // Default sigmoid: lower asymptote 0, higher asymptote 1.
        final UnivariateFunction sigmoid = new Sigmoid();

        // The default sigmoid is symmetric about x = 0, where it equals the midpoint 0.5,
        // and saturates to its asymptotes at the infinities.
        Assert.assertEquals(0.5, sigmoid.value(0), ONE_ULP);
        Assert.assertEquals(0, sigmoid.value(Double.NEGATIVE_INFINITY), ONE_ULP);
        Assert.assertEquals(1, sigmoid.value(Double.POSITIVE_INFINITY), ONE_ULP);
    }

    @Test
    public void testDerivative() {
        final Sigmoid sigmoid = new Sigmoid();

        // Evaluate the default sigmoid and its derivative at x = 0
        // (1 variable, order 1, variable index 0, value 0.0).
        final DerivativeStructure resultAtZero =
            sigmoid.value(new DerivativeStructure(1, 1, 0, 0.0));

        // The first derivative of the default sigmoid peaks at x = 0 with value 0.25.
        Assert.assertEquals(0.25, resultAtZero.getPartialDerivative(1), EXACT);
    }

    @Test
    public void testDerivativesHighOrder() {
        // Sigmoid with asymptotes [1, 3], evaluated together with its first five
        // derivatives (1 variable, order 5, variable index 0, value 1.2).
        final DerivativeStructure result =
            new Sigmoid(1, 3).value(new DerivativeStructure(1, 5, 0, 1.2));

        // Reference values for the function and its successive derivatives at x = 1.2.
        Assert.assertEquals( 2.5370495669980352859,  result.getPartialDerivative(0), 5.0e-16);
        Assert.assertEquals( 0.35578888129361140441, result.getPartialDerivative(1), 6.0e-17);
        Assert.assertEquals(-0.19107626464144938116, result.getPartialDerivative(2), 6.0e-17);
        Assert.assertEquals(-0.02396830286286711696, result.getPartialDerivative(3), 4.0e-17);
        Assert.assertEquals( 0.21682059798981049049, result.getPartialDerivative(4), 3.0e-17);
        Assert.assertEquals(-0.19186320234632658055, result.getPartialDerivative(5), 2.0e-16);
    }

    @Test
    public void testDerivativeLargeArguments() {
        final Sigmoid sigmoid = new Sigmoid(1, 2);

        // For arguments far from the origin (and at the extreme finite/infinite values),
        // the sigmoid is saturated, so its first derivative vanishes.
        final double[] largeArguments = {
            Double.NEGATIVE_INFINITY,
            -Double.MAX_VALUE,
            -1e50,
            -1e3,
            1e3,
            1e50,
            Double.MAX_VALUE,
            Double.POSITIVE_INFINITY
        };

        for (final double x : largeArguments) {
            final double firstDerivative =
                sigmoid.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1);
            Assert.assertEquals(0, firstDerivative, EXACT);
        }
    }

    @Test(expected = NullArgumentException.class)
    public void testParametricUsageValueRejectsNullParameters() {
        final Sigmoid.Parametric function = new Sigmoid.Parametric();
        function.value(0, (double[]) null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsageValueRejectsWrongParameterCount() {
        final Sigmoid.Parametric function = new Sigmoid.Parametric();
        // value() requires exactly two parameters (lower and higher asymptote).
        function.value(0, new double[] {0});
    }

    @Test(expected = NullArgumentException.class)
    public void testParametricUsageGradientRejectsNullParameters() {
        final Sigmoid.Parametric function = new Sigmoid.Parametric();
        function.gradient(0, (double[]) null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricUsageGradientRejectsWrongParameterCount() {
        final Sigmoid.Parametric function = new Sigmoid.Parametric();
        // gradient() requires exactly two parameters (lower and higher asymptote).
        function.gradient(0, new double[] {0});
    }

    @Test
    public void testParametricValue() {
        final double lo = 2;
        final double hi = 3;

        // A configured Sigmoid and the Parametric form fed the same asymptotes
        // must agree at every point.
        final Sigmoid sigmoid = new Sigmoid(lo, hi);
        final Sigmoid.Parametric parametric = new Sigmoid.Parametric();
        final double[] asymptotes = {lo, hi};

        Assert.assertEquals(sigmoid.value(-1), parametric.value(-1, asymptotes), EXACT);
        Assert.assertEquals(sigmoid.value(0),  parametric.value(0, asymptotes),  EXACT);
        Assert.assertEquals(sigmoid.value(2),  parametric.value(2, asymptotes),  EXACT);
    }
}
