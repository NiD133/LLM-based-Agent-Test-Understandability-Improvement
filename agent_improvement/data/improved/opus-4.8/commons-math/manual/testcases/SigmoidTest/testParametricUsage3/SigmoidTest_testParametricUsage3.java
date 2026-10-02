package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

/**
 * Verifies the parameter-validation behaviour of {@link Sigmoid.Parametric#gradient(double, double...)}.
 */
public class SigmoidTest_testParametricUsage3 {

    /**
     * The gradient must reject a {@code null} parameter array by throwing
     * {@link NullArgumentException}, rather than failing with a generic NPE.
     */
    @Test(expected = NullArgumentException.class)
    public void gradientWithNullParametersThrowsNullArgumentException() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        final double anyInputValue = 0;
        final double[] nullParameters = null;

        sigmoid.gradient(anyInputValue, nullParameters);
    }
}
