package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.NullArgumentException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage1 {

    @Test(expected = NullArgumentException.class)
    public void testParametricUsage1() {
        Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        sigmoid.value(0, null);
    }
}
