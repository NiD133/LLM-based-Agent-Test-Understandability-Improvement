package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies that {@link LoessInterpolator} reproduces a perfectly linear data
 * set: when the input points already lie on a straight line, the smoothing
 * step must leave each y-value (essentially) unchanged.
 */
public class LoessInterpolatorTest_testOnStraightLine {

    /** Loess bandwidth: fraction of points used for each local regression. */
    private static final double BANDWIDTH = 0.6;
    /** Number of robustness (re-weighting) iterations. */
    private static final int ROBUSTNESS_ITERS = 2;
    /** Accuracy below which a weight is treated as zero. */
    private static final double ACCURACY = 1e-12;
    /** Tolerance for comparing the smoothed values against the originals. */
    private static final double TOLERANCE = 1e-8;

    @Test
    public void testOnStraightLine() {
        // Points on the line y = 2x; smoothing should return them unchanged.
        final double[] xval = { 1, 2, 3, 4, 5 };
        final double[] yval = { 2, 4, 6, 8, 10 };

        final LoessInterpolator interpolator =
                new LoessInterpolator(BANDWIDTH, ROBUSTNESS_ITERS, ACCURACY);
        final double[] smoothed = interpolator.smooth(xval, yval);

        Assert.assertEquals(xval.length, smoothed.length);
        for (int i = 0; i < smoothed.length; ++i) {
            Assert.assertEquals(yval[i], smoothed[i], TOLERANCE);
        }
    }
}
