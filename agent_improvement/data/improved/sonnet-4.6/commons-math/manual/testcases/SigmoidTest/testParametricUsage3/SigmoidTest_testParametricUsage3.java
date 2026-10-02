package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

/**
 * Tests that {@link Sigmoid.Parametric#gradient} enforces a non-null parameter array.
 */
public class SigmoidTest_testParametricUsage3 {

    /**
     * Passing a null parameter array to {@code Sigmoid.Parametric.gradient} must
     * throw {@link NullArgumentException} rather than a generic NullPointerException,
     * so callers receive a meaningful error.
     */
    @Test(expected = NullArgumentException.class)
    public void testParametricUsage3() {
        final Sigmoid.Parametric parametric = new Sigmoid.Parametric();
        // Null parameters should be rejected with a typed exception.
        parametric.gradient(0, null);
    }
}
