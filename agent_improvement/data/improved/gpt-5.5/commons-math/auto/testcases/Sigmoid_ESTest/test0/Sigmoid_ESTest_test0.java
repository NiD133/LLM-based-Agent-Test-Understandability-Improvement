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
public class Sigmoid_ESTest_test0 extends Sigmoid_ESTest_scaffolding {

    private static final double LOWER_ASYMPTOTE = 33.182076530840796;
    private static final double UPPER_ASYMPTOTE = 1.2246467991473532E-16;
    private static final int FREE_PARAMETERS = 620;
    private static final int DERIVATION_ORDER = 0;
    private static final double INPUT_VALUE = -1023.143076;
    private static final double ASSERTION_TOLERANCE = 0.01;

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        Sigmoid sigmoid = new Sigmoid(LOWER_ASYMPTOTE, UPPER_ASYMPTOTE);
        DerivativeStructure input = new DerivativeStructure(FREE_PARAMETERS, DERIVATION_ORDER, INPUT_VALUE);

        DerivativeStructure result = sigmoid.value(input);

        assertEquals(LOWER_ASYMPTOTE, result.getValue(), ASSERTION_TOLERANCE);
    }
}
