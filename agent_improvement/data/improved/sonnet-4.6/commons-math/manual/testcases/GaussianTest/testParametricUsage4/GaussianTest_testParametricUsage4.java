package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class GaussianTest_testParametricUsage4 {

    // Verifies that passing null as the parameters array to gradient() throws NullArgumentException.
    @Test(expected = NullArgumentException.class)
    public void testParametricUsage4() {
        final Gaussian.Parametric g = new Gaussian.Parametric();
        g.gradient(0, null);
    }
}
