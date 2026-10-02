package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class GaussianTest_testParametricUsage4 {

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage4() {
        final Gaussian.Parametric gaussianParameters = new Gaussian.Parametric();

        gaussianParameters.gradient(0, null);
    }
}
