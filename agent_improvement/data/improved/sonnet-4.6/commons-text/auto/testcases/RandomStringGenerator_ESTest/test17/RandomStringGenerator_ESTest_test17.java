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
public class RandomStringGenerator_ESTest_test17 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that calling selectFrom() with an empty char array while accumulate mode is enabled
     * does not throw, and that the DEFAULT_MINIMUM_CODE_POINT constant equals 0.
     */
    @Test(timeout = 4000)
    public void test_selectFromEmptyCharsWithAccumulateEnabled_defaultMinCodePointIsZero() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();
        char[] emptyChars = new char[0];

        builder.setAccumulate(true);
        RandomStringGenerator.Builder builderAfterSelect = builder.selectFrom(emptyChars);

        assertEquals(0, RandomStringGenerator.Builder.DEFAULT_MINIMUM_CODE_POINT);
    }
}
