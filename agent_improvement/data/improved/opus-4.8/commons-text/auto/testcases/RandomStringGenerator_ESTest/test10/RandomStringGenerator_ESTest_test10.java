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
public class RandomStringGenerator_ESTest_test10 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Calling {@code withinRange(0, 0)} on a builder is a valid configuration
     * (minimum equals maximum and both are non-negative), so the call succeeds.
     * This does not change the builder's {@code DEFAULT_LENGTH} constant, which
     * remains 0.
     */
    @Test(timeout = 4000)
    public void withinRangeZeroToZero_keepsDefaultLengthZero() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        builder.withinRange(0, 0);

        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_LENGTH);
    }
}
