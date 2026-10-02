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
 * Test of the {@link LoessInterpolator} class.
 *
 * <p>{@code LoessInterpolator} performs local regression (LOESS) smoothing. It is
 * configured with three parameters:</p>
 * <ul>
 *   <li><b>bandwidth</b> &mdash; fraction (in {@code (0, 1]}) of the points used for each
 *       local regression; larger values produce smoother curves.</li>
 *   <li><b>robustnessIters</b> &mdash; number of additional robustness iterations that
 *       down-weight outliers.</li>
 *   <li><b>accuracy</b> &mdash; threshold below which weights are treated as zero.</li>
 * </ul>
 */
public class LoessInterpolatorTest {

    /** Accuracy threshold used by every interpolator built in this test. */
    private static final double ACCURACY = 1e-12;

    @Test
    public void testOnOnePoint() {
        final double[] xval = {0.5};
        final double[] yval = {0.7};

        final double[] smoothed = new LoessInterpolator().smooth(xval, yval);

        // A single point cannot be smoothed: it is returned unchanged.
        Assert.assertEquals(1, smoothed.length);
        Assert.assertEquals(0.7, smoothed[0], 0.0);
    }

    @Test
    public void testOnTwoPoints() {
        final double[] xval = {0.5, 0.6};
        final double[] yval = {0.7, 0.8};

        final double[] smoothed = new LoessInterpolator().smooth(xval, yval);

        // Two points already lie on a line, so they are returned unchanged.
        Assert.assertEquals(2, smoothed.length);
        Assert.assertEquals(0.7, smoothed[0], 0.0);
        Assert.assertEquals(0.8, smoothed[1], 0.0);
    }

    @Test
    public void testOnStraightLine() {
        // Points sampled from the line y = 2x.
        final double[] xval = {1, 2, 3, 4, 5};
        final double[] yval = {2, 4, 6, 8, 10};

        final LoessInterpolator interpolator = new LoessInterpolator(0.6, 2, ACCURACY);
        final double[] smoothed = interpolator.smooth(xval, yval);

        // Smoothing perfectly linear data must reproduce the original line.
        Assert.assertEquals(5, smoothed.length);
        for (int i = 0; i < 5; ++i) {
            Assert.assertEquals(yval[i], smoothed[i], 1e-8);
        }
    }

    @Test
    public void testOnDistortedSine() {
        final int numPoints = 100;
        final double[] xval = new double[numPoints];
        final double[] yval = new double[numPoints];
        final double xnoise = 0.1;
        final double ynoise = 0.2;

        generateSineData(xval, yval, xnoise, ynoise);

        final LoessInterpolator interpolator = new LoessInterpolator(0.3, 4, ACCURACY);
        final double[] smoothed = interpolator.smooth(xval, yval);

        // The smoothed curve should be closer to the "real" sine than the noisy
        // input is, i.e. its sum of squared residuals should be smaller.
        double noisyResidualSum = 0;
        double fitResidualSum = 0;
        for (int i = 0; i < numPoints; ++i) {
            final double expected = JdkMath.sin(xval[i]);
            noisyResidualSum += JdkMath.pow(yval[i] - expected, 2);
            fitResidualSum += JdkMath.pow(smoothed[i] - expected, 2);
        }

        Assert.assertTrue(fitResidualSum < noisyResidualSum);
    }

    @Test
    public void testIncreasingBandwidthIncreasesSmoothness() {
        final int numPoints = 100;
        final double[] xval = new double[numPoints];
        final double[] yval = new double[numPoints];
        final double xnoise = 0.1;
        final double ynoise = 0.1;

        generateSineData(xval, yval, xnoise, ynoise);

        // For an increasing sequence of bandwidths, measure the roughness of each
        // smoothed curve as the sum of squared differences between adjacent points.
        final double[] bandwidths = {0.1, 0.5, 1.0};
        final double[] variances = new double[bandwidths.length];
        for (int i = 0; i < bandwidths.length; i++) {
            final LoessInterpolator interpolator = new LoessInterpolator(bandwidths[i], 4, ACCURACY);
            final double[] smoothed = interpolator.smooth(xval, yval);

            for (int j = 1; j < smoothed.length; ++j) {
                variances[i] += JdkMath.pow(smoothed[j] - smoothed[j - 1], 2);
            }
        }

        // A larger bandwidth must yield a smoother (lower variance) curve.
        for (int i = 1; i < variances.length; ++i) {
            Assert.assertTrue(variances[i] < variances[i - 1]);
        }
    }

    @Test
    public void testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers() {
        final int numPoints = 100;
        final double[] xval = new double[numPoints];
        final double[] yval = new double[numPoints];
        final double xnoise = 0.1;
        final double ynoise = 0.1;

        generateSineData(xval, yval, xnoise, ynoise);

        // Introduce a couple of outliers.
        yval[numPoints / 3] *= 100;
        yval[2 * numPoints / 3] *= -100;

        // For an increasing number of robustness iterations, measure the roughness of
        // each smoothed curve as the sum of absolute differences between adjacent points.
        final int maxRobustnessIters = 4;
        final double[] variances = new double[maxRobustnessIters];
        for (int robustnessIters = 0; robustnessIters < maxRobustnessIters; robustnessIters++) {
            final LoessInterpolator interpolator = new LoessInterpolator(0.3, robustnessIters, ACCURACY);
            final double[] smoothed = interpolator.smooth(xval, yval);

            for (int j = 1; j < smoothed.length; ++j) {
                variances[robustnessIters] += JdkMath.abs(smoothed[j] - smoothed[j - 1]);
            }
        }

        // More robustness iterations must better suppress the outliers, yielding a
        // smoother (lower variance) curve.
        for (int i = 1; i < variances.length; ++i) {
            Assert.assertTrue(variances[i] < variances[i - 1]);
        }
    }

    @Test(expected = DimensionMismatchException.class)
    public void testUnequalSizeArguments() {
        // x and y must have the same length.
        new LoessInterpolator().smooth(new double[] {1, 2, 3}, new double[] {1, 2, 3, 4});
    }

    @Test(expected = NoDataException.class)
    public void testEmptyData() {
        new LoessInterpolator().smooth(new double[] {}, new double[] {});
    }

    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing1() {
        // x is not monotonically ordered.
        new LoessInterpolator().smooth(new double[] {4, 3, 1, 2}, new double[] {3, 4, 5, 6});
    }

    @Test(expected = NonMonotonicSequenceException.class)
    public void testNonStrictlyIncreasing2() {
        // x contains a repeated value, so it is not strictly increasing.
        new LoessInterpolator().smooth(new double[] {1, 2, 2, 3}, new double[] {3, 4, 5, 6});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal1() {
        new LoessInterpolator().smooth(new double[] {1, 2, Double.NaN}, new double[] {3, 4, 5});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal2() {
        new LoessInterpolator().smooth(new double[] {1, 2, Double.POSITIVE_INFINITY}, new double[] {3, 4, 5});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal3() {
        new LoessInterpolator().smooth(new double[] {1, 2, Double.NEGATIVE_INFINITY}, new double[] {3, 4, 5});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal4() {
        new LoessInterpolator().smooth(new double[] {3, 4, 5}, new double[] {1, 2, Double.NaN});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal5() {
        new LoessInterpolator().smooth(new double[] {3, 4, 5}, new double[] {1, 2, Double.POSITIVE_INFINITY});
    }

    @Test(expected = NotFiniteNumberException.class)
    public void testNotAllFiniteReal6() {
        new LoessInterpolator().smooth(new double[] {3, 4, 5}, new double[] {1, 2, Double.NEGATIVE_INFINITY});
    }

    @Test(expected = NumberIsTooSmallException.class)
    public void testInsufficientBandwidth() {
        // A bandwidth of 0.1 selects too few points (here 1 of 12) to fit a local line.
        final LoessInterpolator interpolator = new LoessInterpolator(0.1, 3, ACCURACY);
        interpolator.smooth(new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
                            new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
    }

    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth1() {
        // Bandwidth must lie in (0, 1]; a negative value is rejected.
        new LoessInterpolator(-0.2, 3, ACCURACY);
    }

    @Test(expected = OutOfRangeException.class)
    public void testCompletelyIncorrectBandwidth2() {
        // Bandwidth must lie in (0, 1]; a value greater than 1 is rejected.
        new LoessInterpolator(1.1, 3, ACCURACY);
    }

    @Test
    public void testMath296withoutWeights() {
        final double[] xval = {
            0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
            1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0
        };
        final double[] yval = {
            0.47, 0.48, 0.55, 0.56, -0.08, -0.04, -0.07, -0.07,
            -0.56, -0.46, -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04, 3.54, 3.46, 3.36, 3.35
        };
        // Reference output from R, rounded to .001.
        final double[] yref = {
            0.461, 0.499, 0.541, 0.308, 0.175, -0.042, -0.072,
            -0.196, -0.311, -0.446, -0.557, -1.497, -2.133,
            -3.08, -3.09, -0.621, 0.982, 3.449, 3.389, 3.336
        };

        final LoessInterpolator interpolator = new LoessInterpolator(0.3, 4, ACCURACY);
        final double[] smoothed = interpolator.smooth(xval, yval);

        Assert.assertEquals(xval.length, smoothed.length);
        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(yref[i], smoothed[i], 0.02);
        }
    }

    // MATH-1379
    @Test
    public void testFitWithUnevenXSpacing() {
        final double[] xval = {
            0.1, 0.12, 0.23, 0.4, 0.57,
            0.7, 0.87, 1.3, 1.9, 2.2,
            2.3, 2.65, 3.0, 3.1, 3.5,
            4.6, 4.7, 5.8, 5.95, 6.1
        };
        final double[] yval = {
            0.47, 0.48, 0.55, 0.56, -0.08,
            -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04, 3.54, 3.46, 3.36, 3.35
        };

        // Compare with output from R.
        // predict(loess(y ~ x, data.frame(x=xval, y=yval), span=0.35, degree=1, family="symmetric", control=loess.control(iterations=1, surface="direct")))
        final double[] yref = {
            0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445,
            0.002615653, -0.031178445, -0.187124310, -0.405235207, -0.535023851,
            -0.706801740, -1.466740294, -2.349248503, -2.596576469, -3.354222419,
            0.086206868, 0.320251370, 3.064778450, 3.426179479, 3.783500164
        };

        final double delta = 1e-8;
        // Note R counts all iterations whereas LoessInterpolator robustness iterations are
        // in addition to the initial fit.
        final LoessInterpolator interpolator = new LoessInterpolator(0.35, 0, ACCURACY);
        final double[] smoothed = interpolator.smooth(xval, yval);

        Assert.assertEquals(xval.length, smoothed.length);
        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(yref[i], smoothed[i], delta);
        }
    }

    // MATH-1379
    @Test
    public void testFitWithVaryingWeightsAndUnevenXSpacing() {
        final double[] xval = {
            0.1, 0.12, 0.23, 0.4, 0.57,
            0.7, 0.87, 1.3, 1.9, 2.2,
            2.3, 2.65, 3.0, 3.1, 3.5,
            4.6, 4.7, 5.8, 5.95, 6.1
        };
        final double[] yval = {
            0.47, 0.48, 0.55, 0.56, -0.08,
            -0.04, -0.07, -0.07, -0.56, -0.46,
            -0.56, -0.52, -3.03, -3.08, -3.09,
            -3.04, 3.54, 3.46, 3.36, 3.35
        };
        final double[] weights = {
            1, 1, 1, 1, 1,
            1, 1, 1, 1, 1,
            1, 0.8, 0.5, 0.5, 0.8,
            0.8, 0.5, 0.5, 0.9, 1
        };

        // Compare with output from R.
        // predict(loess(y ~ x, data.frame(x=xval, y=yval), weights, span=0.35, degree=1, family="symmetric", control=loess.control(iterations=1, surface="direct")))
        final double[] yref = {
            0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445,
            0.002615653, -0.031178445, -0.187124310, -0.406403569, -0.531957113,
            -0.669978426, -1.411039850, -2.225022609, -2.463874517, -3.298767758,
            -0.506116921, -0.231242991, 2.873755984, 3.295897106, 3.715495321
        };

        final double delta = 1e-8;
        // Note R counts all iterations whereas LoessInterpolator robustness iterations are
        // in addition to the initial fit.
        final LoessInterpolator interpolator = new LoessInterpolator(0.35, 0, ACCURACY);
        final double[] smoothed = interpolator.smooth(xval, yval, weights);

        Assert.assertEquals(xval.length, smoothed.length);
        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(yref[i], smoothed[i], delta);
        }
    }

    /**
     * Fills {@code xval} and {@code yval} with one period of a sine wave that is jittered
     * with random noise.
     *
     * @param xval   array to receive the abscissas (monotonically increasing).
     * @param yval   array to receive the noisy sine ordinates.
     * @param xnoise relative jitter applied to the x spacing.
     * @param ynoise absolute jitter applied to the y values.
     */
    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        final double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }
}
