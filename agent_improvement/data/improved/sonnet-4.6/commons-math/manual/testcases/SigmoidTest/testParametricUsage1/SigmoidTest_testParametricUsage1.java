package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage1 {

    /**
     * Passing null as the parameters array to Sigmoid.Parametric.value() must
     * throw NullArgumentException rather than producing a silent wrong result.
     */
    @Test(expected = NullArgumentException.class)
    public void testParametricUsage1() {
        final Sigmoid.Parametric g = new Sigmoid.Parametric();
        g.value(0, null);
    }
}
