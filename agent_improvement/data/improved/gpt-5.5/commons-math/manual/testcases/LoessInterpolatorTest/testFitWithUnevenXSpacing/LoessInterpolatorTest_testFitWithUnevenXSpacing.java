package org.apache.commons.math4.legacy.analysis.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class LoessInterpolatorTest_testFitWithUnevenXSpacing {

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

        // Compare with output from R:
        // predict(loess(y ~ x, data.frame(x=xval, y=yval), span=0.35,
        //               degree=1, family="symmetric",
        //               control=loess.control(iterations=1, surface="direct")))
        final double[] yref = {
            0.556184894, 0.541907126, 0.455059334, 0.303681477, 0.142126445,
            0.002615653, -0.031178445, -0.187124310, -0.405235207, -0.535023851,
            -0.706801740, -1.466740294, -2.349248503, -2.596576469, -3.354222419,
            0.086206868, 0.320251370, 3.064778450, 3.426179479, 3.783500164
        };
        final double delta = 1e-8;

        // R counts all iterations, while LoessInterpolator robustness iterations
        // are in addition to the initial fit.
        final LoessInterpolator li = new LoessInterpolator(0.35, 0, 1e-12);
        final double[] res = li.smooth(xval, yval);

        Assert.assertEquals(xval.length, res.length);
        for (int i = 0; i < res.length; ++i) {
            Assert.assertEquals(yref[i], res[i], delta);
        }
    }
}
