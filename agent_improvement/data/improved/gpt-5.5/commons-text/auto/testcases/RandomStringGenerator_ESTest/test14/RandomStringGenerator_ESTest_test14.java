package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.IntUnaryOperator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test14 extends RandomStringGenerator_ESTest_scaffolding {

    private static final char[][] RANGE_WITH_INVALID_PAIR_WIDTH = new char[1][6];

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        try {
            builder.withinRange(RANGE_WITH_INVALID_PAIR_WIDTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Each pair must contain minimum and maximum code point
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
