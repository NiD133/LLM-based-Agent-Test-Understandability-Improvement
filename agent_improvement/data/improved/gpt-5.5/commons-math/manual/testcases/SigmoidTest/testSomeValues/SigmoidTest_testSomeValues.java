package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.UnivariateFunction;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testSomeValues {

    private static final double TOLERANCE = Math.ulp(1d);

    @Test
    public void testSomeValues() {
        final UnivariateFunction sigmoid = new Sigmoid();

        Assert.assertEquals(0.5, sigmoid.value(0), TOLERANCE);
        Assert.assertEquals(0, sigmoid.value(Double.NEGATIVE_INFINITY), TOLERANCE);
        Assert.assertEquals(1, sigmoid.value(Double.POSITIVE_INFINITY), TOLERANCE);
    }
}
