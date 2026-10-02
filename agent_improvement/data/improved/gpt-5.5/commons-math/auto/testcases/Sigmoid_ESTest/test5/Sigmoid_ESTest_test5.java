package org.apache.commons.math4.legacy.analysis.function;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.math4.legacy.analysis.differentiation.DerivativeStructure;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Sigmoid_ESTest_test5 extends Sigmoid_ESTest_scaffolding {

    private static final double LOWER_ASYMPTOTE = 0.06666666666666667;
    private static final double UPPER_ASYMPTOTE = 0.0;
    private static final double INPUT_VALUE = 0.625;
    private static final double EXPECTED_SIGMOID_VALUE = 0.023243009022263054;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        Sigmoid sigmoid = new Sigmoid(LOWER_ASYMPTOTE, UPPER_ASYMPTOTE);

        double actualSigmoidValue = sigmoid.applyAsDouble(INPUT_VALUE);

        assertEquals(EXPECTED_SIGMOID_VALUE, actualSigmoidValue, ASSERTION_TOLERANCE);
    }
}
