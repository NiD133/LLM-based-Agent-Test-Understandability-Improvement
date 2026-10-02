package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class GaussianTest_testParametricUsage1 {

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage1() {
        final Gaussian.Parametric parametricGaussian = new Gaussian.Parametric();

        parametricGaussian.value(0, null);
    }
}
