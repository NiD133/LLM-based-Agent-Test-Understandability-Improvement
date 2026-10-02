package org.apache.commons.math4.legacy.analysis.function;

import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.junit.Assert;
import org.junit.Test;

public class SigmoidTest_testDerivative {

    @Test
    public void testDerivative() {
        final Sigmoid sigmoid = new Sigmoid();
        final DerivativeStructure argumentAtZero =
                new DerivativeStructure(1, 1, 0, 0.0);

        final DerivativeStructure valueAtZero = sigmoid.value(argumentAtZero);

        Assert.assertEquals(0.25, valueAtZero.getPartialDerivative(1), 0);
    }
}
