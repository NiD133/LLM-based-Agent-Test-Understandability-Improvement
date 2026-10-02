package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class GaussianTest_testParametricUsage1 {

    /**
     * Verifies that passing null as the parameters array to Gaussian.Parametric.value()
     * throws NullArgumentException rather than NullPointerException or silently failing.
     */
    @Test(expected = NullArgumentException.class)
    public void testParametricUsage1() {
        final Gaussian.Parametric parametric = new Gaussian.Parametric();
        final double[] nullParameters = null;
        parametric.value(0, nullParameters);
    }
}
