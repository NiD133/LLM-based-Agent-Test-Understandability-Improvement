package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

/**
 * Verifies the parameter validation performed by {@link Sigmoid.Parametric}.
 */
public class SigmoidTest_testParametricUsage1 {

    /**
     * {@link Sigmoid.Parametric#value(double, double[])} must reject a {@code null}
     * parameter array by throwing a {@link NullArgumentException}, rather than
     * dereferencing it and failing with a generic NullPointerException.
     */
    @Test(expected = NullArgumentException.class)
    public void valueRejectsNullParameterArray() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        final double anyInputPoint = 0;
        final double[] missingParameters = null;
        sigmoid.value(anyInputPoint, missingParameters);
    }
}
