package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage3 {

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage3() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        sigmoid.gradient(0, null);
    }
}
