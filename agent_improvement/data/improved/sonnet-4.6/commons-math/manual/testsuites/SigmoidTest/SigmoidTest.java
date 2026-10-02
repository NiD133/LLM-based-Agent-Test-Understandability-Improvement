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
 *
 * <p>The standard sigmoid maps any real number to (0, 1).
 * The two-parameter form {@code Sigmoid(lo, hi)} maps to (lo, hi).
 */
public class SigmoidTest {

    /** Tolerance equal to one unit in the last place of 1.0 (machine epsilon). */
    private final double EPS = Math.ulp(1d);

    // -------------------------------------------------------------------------
    // Standard sigmoid: value tests
    // -------------------------------------------------------------------------

    @Test
    public void testStandardSigmoidAtKeyPoints() {
        final UnivariateFunction sigmoid = new Sigmoid();

        // Midpoint: sigmoid(0) = 0.5
        Assert.assertEquals(0.5, sigmoid.value(0), EPS);
        // Lower asymptote: sigmoid(-∞) → 0
        Assert.assertEquals(0, sigmoid.value(Double.NEGATIVE_INFINITY), EPS);
        // Upper asymptote: sigmoid(+∞) → 1
        Assert.assertEquals(1, sigmoid.value(Double.POSITIVE_INFINITY), EPS);
    }

    // -------------------------------------------------------------------------
    // Derivative tests via DerivativeStructure
    // -------------------------------------------------------------------------

    @Test
    public void testFirstDerivativeAtZero() {
        final Sigmoid sigmoid = new Sigmoid();
        // Evaluate sigmoid at x=0 tracking 1 variable up to order 1
        final DerivativeStructure result = sigmoid.value(new DerivativeStructure(1, 1, 0, 0.0));

        // sigmoid'(0) = sigmoid(0) * (1 - sigmoid(0)) = 0.5 * 0.5 = 0.25
        Assert.assertEquals(0.25, result.getPartialDerivative(1), 0);
    }

    @Test
    public void testHighOrderDerivativesAtPositiveInput() {
        // Sigmoid(lo=1, hi=3) evaluated at x=1.2, tracking derivatives up to order 5
        final DerivativeStructure result =
                new Sigmoid(1, 3).value(new DerivativeStructure(1, 5, 0, 1.2));

        final double value      =  2.5370495669980352859;
        final double deriv1     =  0.35578888129361140441;
        final double deriv2     = -0.19107626464144938116;
        final double deriv3     = -0.02396830286286711696;
        final double deriv4     =  0.21682059798981049049;
        final double deriv5     = -0.19186320234632658055;

        Assert.assertEquals(value,  result.getPartialDerivative(0), 5.0e-16);
        Assert.assertEquals(deriv1, result.getPartialDerivative(1), 6.0e-17);
        Assert.assertEquals(deriv2, result.getPartialDerivative(2), 6.0e-17);
        Assert.assertEquals(deriv3, result.getPartialDerivative(3), 4.0e-17);
        Assert.assertEquals(deriv4, result.getPartialDerivative(4), 3.0e-17);
        Assert.assertEquals(deriv5, result.getPartialDerivative(5), 2.0e-16);
    }

    @Test
    public void testFirstDerivativeIsZeroAtExtremeInputs() {
        // For very large |x| the sigmoid saturates, so its derivative → 0.
        final Sigmoid sigmoid = new Sigmoid(1, 2);

        final double[] extremeInputs = {
            Double.NEGATIVE_INFINITY,
            -Double.MAX_VALUE,
            -1e50,
            -1e3,
             1e3,
             1e50,
             Double.MAX_VALUE,
            Double.POSITIVE_INFINITY
        };

        for (double x : extremeInputs) {
            final double derivative =
                    sigmoid.value(new DerivativeStructure(1, 1, 0, x)).getPartialDerivative(1);
            Assert.assertEquals("Expected derivative 0 at x=" + x, 0, derivative, 0);
        }
    }

    // -------------------------------------------------------------------------
    // Parametric form: input validation
    // -------------------------------------------------------------------------

    @Test(expected = NullArgumentException.class)
    public void testParametricValueThrowsOnNullParameters() {
        new Sigmoid.Parametric().value(0, null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricValueThrowsOnWrongNumberOfParameters() {
        // Parametric sigmoid requires exactly 2 parameters [lo, hi]; 1 is wrong.
        new Sigmoid.Parametric().value(0, new double[] {0});
    }

    @Test(expected = NullArgumentException.class)
    public void testParametricGradientThrowsOnNullParameters() {
        new Sigmoid.Parametric().gradient(0, null);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testParametricGradientThrowsOnWrongNumberOfParameters() {
        new Sigmoid.Parametric().gradient(0, new double[] {0});
    }

    // -------------------------------------------------------------------------
    // Parametric form: value consistency with constructor form
    // -------------------------------------------------------------------------

    @Test
    public void testParametricValueMatchesConstructorForm() {
        final double lo = 2;
        final double hi = 3;
        final Sigmoid direct = new Sigmoid(lo, hi);
        final Sigmoid.Parametric parametric = new Sigmoid.Parametric();
        final double[] params = {lo, hi};

        // The parametric API must produce the same output as the direct constructor.
        Assert.assertEquals(direct.value(-1), parametric.value(-1, params), 0);
        Assert.assertEquals(direct.value( 0), parametric.value( 0, params), 0);
        Assert.assertEquals(direct.value( 2), parametric.value( 2, params), 0);
    }
}
