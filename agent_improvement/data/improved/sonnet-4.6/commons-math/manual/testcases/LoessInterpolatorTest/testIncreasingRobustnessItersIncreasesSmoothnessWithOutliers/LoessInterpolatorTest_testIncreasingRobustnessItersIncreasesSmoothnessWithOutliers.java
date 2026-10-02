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

public class LoessInterpolatorTest_testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers {

    // Outlier scale factors: one point is amplified 100x, another is negated 100x
    private static final double POSITIVE_OUTLIER_SCALE = 100;
    private static final double NEGATIVE_OUTLIER_SCALE = -100;

    // LOESS parameters
    private static final double BANDWIDTH = 0.3;
    private static final double ACCURACY = 1e-12;
    private static final int MAX_ROBUSTNESS_ITERS = 4;

    private void generateSineData(double[] xval, double[] yval, double xnoise, double ynoise) {
        double dx = 2 * JdkMath.PI / xval.length;
        double x = 0;
        for (int i = 0; i < xval.length; ++i) {
            xval[i] = x;
            yval[i] = JdkMath.sin(x) + (2 * JdkMath.random() - 1) * ynoise;
            x += dx * (1 + (2 * JdkMath.random() - 1) * xnoise);
        }
    }

    /**
     * Verifies that increasing the number of robustness iterations in LOESS smoothing
     * reduces total variation (i.e. produces smoother output) when the input data
     * contains outliers.
     *
     * The test generates noisy sine data, injects two large outliers, then runs LOESS
     * with 0 through 3 robustness iterations. Each additional robustness pass should
     * down-weight the outliers further, yielding a strictly smoother result.
     */
    @Test
    public void testIncreasingRobustnessItersIncreasesSmoothnessWithOutliers() {
        int numPoints = 100;
        double[] xval = new double[numPoints];
        double[] yval = new double[numPoints];
        double xnoise = 0.1;
        double ynoise = 0.1;
        generateSineData(xval, yval, xnoise, ynoise);

        // Introduce two large outliers at one-third and two-thirds of the data range
        int firstOutlierIndex = numPoints / 3;
        int secondOutlierIndex = 2 * numPoints / 3;
        yval[firstOutlierIndex] *= POSITIVE_OUTLIER_SCALE;
        yval[secondOutlierIndex] *= NEGATIVE_OUTLIER_SCALE;

        // Compute total variation of the smoothed output for each robustness iteration count.
        // Total variation = sum of |res[j] - res[j-1]|; lower means smoother.
        double[] totalVariationByRobustnessIters = new double[MAX_ROBUSTNESS_ITERS];
        for (int robustnessIters = 0; robustnessIters < MAX_ROBUSTNESS_ITERS; robustnessIters++) {
            LoessInterpolator loess = new LoessInterpolator(BANDWIDTH, robustnessIters, ACCURACY);
            double[] smoothed = loess.smooth(xval, yval);
            double totalVariation = 0;
            for (int j = 1; j < smoothed.length; ++j) {
                totalVariation += JdkMath.abs(smoothed[j] - smoothed[j - 1]);
            }
            totalVariationByRobustnessIters[robustnessIters] = totalVariation;
        }

        // Each additional robustness pass should strictly reduce total variation
        for (int robustnessIters = 1; robustnessIters < totalVariationByRobustnessIters.length; ++robustnessIters) {
            Assert.assertTrue(
                totalVariationByRobustnessIters[robustnessIters] < totalVariationByRobustnessIters[robustnessIters - 1]
            );
        }
    }
}
