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
package org.apache.commons.math4.legacy.analysis.interpolation;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.apache.commons.math4.legacy.exception.NoDataException;
import org.apache.commons.math4.legacy.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.legacy.exception.NotFiniteNumberException;
import org.apache.commons.math4.legacy.exception.NumberIsTooSmallException;
import org.apache.commons.math4.legacy.exception.OutOfRangeException;
import org.apache.commons.math4.core.jdkmath.JdkMath;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link LoessInterpolator}, which implements the LOESS (Locally Weighted
 * Scatterplot Smoothing) algorithm.
 *
 * <p>The tests cover:
 * <ul>
 *   <li>Correctness on trivial inputs (single point, two points, straight line)</li>
 *   <li>Smoothing quality on noisy sine data</li>
 *   <li>Effect of bandwidth and robustness iterations on smoothness</li>
 *   <li>Regression tests against reference output from R's {@code loess()}</li>
 *   <li>Input validation (unequal sizes, empty data, non-monotonic x, non-finite values,
 *       out-of-range bandwidth)</li>
 * </ul>
 */
public class LoessInterpolatorTest {

    // -----------------------------------------------------------------------
    // Shared interpolator parameters used across multiple tests
    // -----------------------------------------------------------------------

    /** Fraction of data points used in each local regression window. */
    private static final double BANDWIDTH_30_PERCENT = 0.3;

    /** Fraction of data points used for the Math-296 and MATH-1379 regression tests. */
    private static final double BANDWIDTH_35_PERCENT = 0.35;

    /** Number of robustness (re-weighting) iterations that down-weight outliers. */
    private static final int ROBUSTNESS_ITERS_4 = 4;

    /** Convergence threshold for the iterative weight-update step. */
    private static final double ACCURACY_1E_12 = 1e-12;

    // -----------------------------------------------------------------------
    // Correctness on trivial inputs
    // -----------------------------------------------------------------------

    /**
     * A single data point must be returned unchanged: LOESS has no neighbours
     * to regress against, so the smoothed value equals the observed value.
     */
    @Test
    public void testOnOnePoint() {
        double[] xval = {0.5};
        double[] yval = {0.7};
        double[] res = new LoessInterpolator().smooth(xval, yval);
        Assert.assertEquals("Smoothed array should contain exactly one element", 1, res.length);
        Assert.assertEquals("Single-point smoothing must reproduce the observed value", 0.7, res[0], 0.0);
    }

    /**
     * Two data points must each be returned unchanged: with only two points the
     * local linear fit passes exactly through both.
     */
    @Test
    public void testOnTwoPoints() {
        double[] xval = {0.5, 0.6};
        double[] yval = {0.7, 0.8};
        double[] res = new LoessInterpolator().smooth(xval, yval);
        Assert.assertEquals("Smoothed array should contain exactly two elements", 2, res.length);
        Assert.assertEquals("First smoothed value must equal first observed value", 0.7, res[0], 0.0);
        Assert.assertEquals("Second smoothed value must equal second observed value", 0.8, res[1], 0.0);
    }

    /**
     * LOESS must reproduce a perfectly straight line exactly (up to floating-point
     * rounding), because any local linear fit of points on a line is that same line.
     */
    @Test
    public void testOnStraightLine() {
        double[] xval = {1, 2, 3, 4, 5};
        double[] yval = {2, 4, 6, 8, 10};
        LoessInterpolator li = new LoessInterpolator(0.6, 2, ACCURACY_1E_12);
        double[] res = li.smooth(xval, yval);
        Assert.assertEquals("Result length must match input length", 5, res.length);
        for (int i = 0; i < 5; ++i) {
            Assert.assertEquals("Smoothed value on a straight line must equal the original y-value",
                    yval[i], res[i], 1e-8);
        }
    }

    // -----------------------------------------------------------------------
    // Smoothing quality on noisy sine data
    // -----------------------------------------------------------------------

    /**
     * Smoothing noisy sine data should reduce the residual relative to the true
     * sine more than the raw (noisy) observations do, i.e. the fit is closer to
     * the ground truth than the jittered input.
     */
    @Test
    public void testOnDistortedSine() {
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        double xnoise = 0.1;
        double ynoise = 0.2;

        generateSineData(xval, yval, xnoise, ynoise);

        LoessInterpolator li = new LoessInterpolator(BANDWIDTH_30_PERCENT, ROBUSTNESS_ITERS_4, ACCURACY_1E_12);
        double[] res = li.smooth(xval, yval);

        // Sum of squared residuals from the true sine for noisy input vs. smoothed output.
        double noisyResidualSum = 0;
        double fitResidualSum = 0;
        for (int i = 0; i < numPoints; ++i) {
            double expected = JdkMath.sin(xval[i]);
            double noisy = yval[i];
            double fit = res[i];

            noisyResidualSum += JdkMath.pow(noisy - expected, 2);
            fitResidualSum   += JdkMath.pow(fit   - expected, 2);
        }

        Assert.assertTrue("Smoothed curve should be closer to true sine than the raw noisy data",
                fitResidualSum < noisyResidualSum);
    }

    /**
     * A wider bandwidth considers more neighbours in each local fit, which produces
     * a smoother (lower total variation) curve.  The test verifies that the total
     * squared successive-difference (a proxy for roughness) decreases as bandwidth
     * increases from 0.1 to 0.5 to 1.0.
     */
    @Test
    public void testIncreasingBandwidthIncreasesSmoothness() {
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        double xnoise = 0.1;
        double ynoise = 0.1;

        generateSineData(xval, yval, xnoise, ynoise);

        double[] bandwidths = {0.1, 0.5, 1.0};
        double[] roughness  = new double[bandwidths.length]; // sum of squared successive differences
        for (int i = 0; i < bandwidths.length; i++) {
            LoessInterpolator li = new LoessInterpolator(bandwidths[i], ROBUSTNESS_ITERS_4, ACCURACY_1E_12);
            double[] res = li.smooth(xval, yval);

            for (int j = 1; j < res.length; ++j) {
                roughness[i] += JdkMath.pow(res[j] - res[j - 1], 2);
            }
        }

        for (int i = 1; i < roughness.length; ++i) {
            Assert.assertTrue(
                    "Roughness should decrease as bandwidth increases (bandwidth[" + i + "]=" + bandwidths[i]
                            + " vs bandwidth[" + (i - 1) + "]=" + bandwidths[i - 1] + ")",
                    roughness[i] < roughness[i - 1]);
        }
    }

    /**
     * When outliers are present, more robustness iterations reduce their influence
     * via iterative re-weighting, yielding a smoother result.  The test verifies
     * that total variation (sum of absolute successive differences) decreases as
     * the number of robustness iterations grows from 0 to 3.
     */
    @Test
    public void testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers() {
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        double xnoise = 0.1;
        double ynoise = 0.1;

        generateSineData(xval, yval, xnoise, ynoise);

        // Introduce two outliers to stress-test robustness iteration down-weighting.
        yval[numPoints / 3]     *=  100;
        yval[2 * numPoints / 3] *= -100;

        double[] totalVariation = new double[4]; // indexed by number of robustness iterations
        for (int i = 0; i < 4; i++) {
            LoessInterpolator li = new LoessInterpolator(BANDWIDTH_30_PERCENT, i, ACCURACY_1E_12);
            double[] res = li.smooth(xval, yval);

            for (int j = 1; j < res.length; ++j) {
                totalVariation[i] += JdkMath.abs(res[j] - res[j - 1]);
            }
        }

        for (int i = 1; i < totalVariation.length; ++i) {
            Assert.assertTrue(
                    "Total variation should decrease as robustness iterations increase (iter " + i
                            + " vs iter " + (i - 1) + ")",
                    totalVariation[i] < totalVariation[i - 1]);
        }
    }

    // -----------------------------------------------------------------------
    // Input validation — expected exceptions
    // -----------------------------------------------------------------------

    /** x and y arrays must have the same length. */
    @Test(expected = DimensionMismatchException.class)
    public void testUnequalSizeArguments() {
        new LoessInterpolator().smooth(new double[] {1, 2, 3}, new double[] {1, 2, 3, 4});
    }

    /** Empty x/y arrays cannot be smoothed. */
    @Test(expected = NoDataException.class)
    public void testEmptyData() {
        new LoessInterpolator().smooth(new double[] {}, new double[] {});
    }

    /** x values must be strictly increasing — decreasing sequence is rejected. */
    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing_descendingXValues() {
        new LoessInterpolator().smooth(new double[] {4, 3, 1, 2}, new double[] {3, 4, 5, 6});
    }

    /** x values must be strictly increasing — duplicate (tied) values are rejected. */
    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing_duplicateXValues() {
        new LoessInterpolator().smooth(new double[] {1, 2, 2, 3}, new double[] {3, 4, 5, 6});
    }

    /** NaN in x array is not a finite real number and must be rejected. */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal_xNaN() {
        new LoessInterpolator().smooth(new double[] {1, 2, Double.NaN}, new double[] {3, 4, 5});
    }

    /** Positive infinity in x array must be rejected. */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal_xPositiveInfinity() {
        new LoessInterpolator().smooth(new double[] {1, 2, Double.POSITIVE_INFINITY}, new double[] {3, 4, 5});
    }

    /** Negative infinity in x array must be rejected. */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal_xNegativeInfinity() {
        new LoessInterpolator().smooth(new double[] {1, 2, Double.NEGATIVE_INFINITY}, new double[] {3, 4, 5});
    }

    /** NaN in y array is not a finite real number and must be rejected. */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal_yNaN() {
        new LoessInterpolator().smooth(new double[] {3, 4, 5}, new double[] {1, 2, Double.NaN});
    }

    /** Positive infinity in y array must be rejected. */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal_yPositiveInfinity() {
        new LoessInterpolator().smooth(new double[] {3, 4, 5}, new double[] {1, 2, Double.POSITIVE_INFINITY});
    }

    /** Negative infinity in y array must be rejected. */
    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal_yNegativeInfinity() {
        new LoessInterpolator().smooth(new double[] {3, 4, 5}, new double[] {1, 2, Double.NEGATIVE_INFINITY});
    }

    /**
     * A bandwidth of 0.1 over 12 data points yields fewer than 2 neighbours,
     * which is insufficient for a local linear regression.
     */
    @Test(expected = NumberIsTooSmallException.class)
    public void testInsufficientBandwidth() {
        LoessInterpolator li = new LoessInterpolator(0.1, 3, ACCURACY_1E_12);
        li.smooth(
                new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
                new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
    }

    /** Bandwidth must be in [0, 1]; a negative value must be rejected. */
    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth_negative() {
        new LoessInterpolator(-0.2, 3, ACCURACY_1E_12);
    }

    /** Bandwidth must be in [0, 1]; a value greater than 1 must be rejected. */
    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth_greaterThanOne() {
        new LoessInterpolator(1.1, 3, ACCURACY_1E_12);
    }

    // -----------------------------------------------------------------------
    // Regression tests against R's loess() — MATH-296 and MATH-1379
    // -----------------------------------------------------------------------

    /**
     * Regression test from MATH-296: verifies smoothed values against reference
     * output from R (rounded to three decimal places), using uniformly spaced x
     * and no observation weights.
     */
    @Test
    public void testMath296withoutWeights() {
        double[] xval = {
            0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
            1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0
        };
        double[] yval = {
            0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07,
            -0.56, -0.46, -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04, 3.54, 3.46, 3.36, 3.35
        };
        // Reference output from R's loess(), rounded to .001.
        double[] yref = {
            0.461, 0.499, 0.541, 0.308, 0.175, -0.042, -0.072,
            -0.196, -0.311, -0.446, -0.557, -1.497, -2.133,
            -3.08, -3.09, -0.621, 0.982, 3.449, 3.389, 3.336
        };

        LoessInterpolator li = new LoessInterpolator(BANDWIDTH_30_PERCENT, ROBUSTNESS_ITERS_4, ACCURACY_1E_12);
        double[] res = li.smooth(xval, yval);
        Assert.assertEquals("Result length must match input length", xval.length, res.length);
        for (int i = 0; i < res.length; ++i) {
            Assert.assertEquals("Smoothed value at index " + i + " must match R reference within 0.02",
                    yref[i], res[i], 0.02);
        }
    }

    /**
     * Regression test from MATH-1379: verifies smoothed values against R reference
     * output using unevenly spaced x values and no observation weights.
     *
     * <p>R command:
     * {@code predict(loess(y ~ x, data.frame(x=xval, y=yval), span=0.35, degree=1,
     * family="symmetric", control=loess.control(iterations=1, surface="direct")))}
     *
     * <p>Note: R counts all iterations, whereas {@code LoessInterpolator} robustness
     * iterations are in addition to the initial fit, so {@code robustnessIters=0} in
     * Java corresponds to {@code iterations=1} in R.
     */
    @Test
    public void testFitWithUnevenXSpacing() {
        final double[] xval = {
            0.1,  0.12, 0.23, 0.4,  0.57,
            0.7,  0.87, 1.3,  1.9,  2.2,
            2.3,  2.65, 3.0,  3.1,  3.5,
            4.6,  4.7,  5.8,  5.95, 6.1
        };
        final double[] yval = {
             0.47,  0.48,  0.55,  0.56, -0.08,
            -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04,  3.54,  3.46,  3.36,  3.35
        };
        // Reference output from R (see method Javadoc for the exact R command).
        final double[] yref = {
             0.556184894,  0.541907126,  0.455059334,  0.303681477,  0.142126445,
             0.002615653, -0.031178445, -0.187124310, -0.405235207, -0.535023851,
            -0.706801740, -1.466740294, -2.349248503, -2.596576469, -3.354222419,
             0.086206868,  0.320251370,  3.064778450,  3.426179479,  3.783500164
        };

        final double delta = 1e-8;
        final LoessInterpolator li = new LoessInterpolator(BANDWIDTH_35_PERCENT, 0, ACCURACY_1E_12);
        final double[] res = li.smooth(xval, yval);
        Assert.assertEquals("Result length must match input length", xval.length, res.length);
        for (int i = 0; i < res.length; ++i) {
            Assert.assertEquals("Smoothed value at index " + i + " must match R reference within " + delta,
                    yref[i], res[i], delta);
        }
    }

    /**
     * Regression test from MATH-1379: verifies smoothed values against R reference
     * output using unevenly spaced x values and non-uniform observation weights.
     *
     * <p>R command:
     * {@code predict(loess(y ~ x, data.frame(x=xval, y=yval), weights, span=0.35, degree=1,
     * family="symmetric", control=loess.control(iterations=1, surface="direct")))}
     *
     * <p>Note: R counts all iterations, whereas {@code LoessInterpolator} robustness
     * iterations are in addition to the initial fit, so {@code robustnessIters=0} in
     * Java corresponds to {@code iterations=1} in R.
     */
    @Test
    public void testFitWithVaryingWeightsAndUnevenXSpacing() {
        final double[] xval = {
            0.1,  0.12, 0.23, 0.4,  0.57,
            0.7,  0.87, 1.3,  1.9,  2.2,
            2.3,  2.65, 3.0,  3.1,  3.5,
            4.6,  4.7,  5.8,  5.95, 6.1
        };
        final double[] yval = {
             0.47,  0.48,  0.55,  0.56, -0.08,
            -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04,  3.54,  3.46,  3.36,  3.35
        };
        // Weights < 1 in the middle and end of the range reduce those points' influence.
        final double[] weights = {
            1,   1,   1,   1,   1,
            1,   1,   1,   1,   1,
            1,   0.8, 0.5, 0.5, 0.8,
            0.8, 0.5, 0.5, 0.9, 1
        };
        // Reference output from R (see method Javadoc for the exact R command).
        final double[] yref = {
             0.556184894,  0.541907126,  0.455059334,  0.303681477,  0.142126445,
             0.002615653, -0.031178445, -0.187124310, -0.406403569, -0.531957113,
            -0.669978426, -1.411039850, -2.225022609, -2.463874517, -3.298767758,
            -0.506116921, -0.231242991,  2.873755984,  3.295897106,  3.715495321
        };

        final double delta = 1e-8;
        final LoessInterpolator li = new LoessInterpolator(BANDWIDTH_35_PERCENT, 0, ACCURACY_1E_12);
        final double[] res = li.smooth(xval, yval, weights);
        Assert.assertEquals("Result length must match input length", xval.length, res.length);
        for (int i = 0; i < res.length; ++i) {
            Assert.assertEquals("Weighted smoothed value at index " + i + " must match R reference within " + delta,
                    yref[i], res[i], delta);
        }
    }

    // -----------------------------------------------------------------------
    // Helper methods
    // -----------------------------------------------------------------------

    /**
     * Populates {@code xval} and {@code yval} with noisy sine-wave data over one
     * full period ({@code [0, 2π]}).  The x spacing is jittered by {@code xnoise}
     * and each y value is perturbed by uniform noise scaled by {@code ynoise}.
     *
     * @param xval   output array for x coordinates (must be pre-allocated)
     * @param yval   output array for y values (must be pre-allocated, same length as xval)
     * @param xnoise relative amplitude of random jitter added to x spacing
     * @param ynoise absolute amplitude of additive uniform noise on y values
     */
    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }
}
