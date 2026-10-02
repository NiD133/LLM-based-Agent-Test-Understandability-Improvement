package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.exception.DimensionMismatchException;
import org.junit.Test;

public class SigmoidTest_testParametricUsage4 {

    private static final double VALUE = 0;
    private static final double[] TOO_FEW_PARAMETERS = { 0 };

    @Test(expected = DimensionMismatchException.class)
    public void gradientRejectsParameterArrayWithMissingUpperBound() {
        final Sigmoid.Parametric sigmoid = new Sigmoid.Parametric();

        sigmoid.gradient(VALUE, TOO_FEW_PARAMETERS);
    }
}
