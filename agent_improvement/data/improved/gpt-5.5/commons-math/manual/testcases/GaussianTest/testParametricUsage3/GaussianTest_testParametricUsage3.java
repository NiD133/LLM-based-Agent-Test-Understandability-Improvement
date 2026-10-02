package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NotStrictlyPositiveException;
import org.junit.Test;

public class GaussianTest_testParametricUsage3 {

    @Test(expected = NotStrictlyPositiveException.class)
    public void testParametricUsage3() {
        final Gaussian.Parametric gaussian = new Gaussian.Parametric();
        final double[] parametersWithNonPositiveSigma = { 0, 1, 0 };

        gaussian.value(0, parametersWithNonPositiveSigma);
    }
}
