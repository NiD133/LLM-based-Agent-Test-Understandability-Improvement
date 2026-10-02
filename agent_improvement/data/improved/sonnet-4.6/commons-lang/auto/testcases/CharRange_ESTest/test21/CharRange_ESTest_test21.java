package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test21 extends CharRange_ESTest_scaffolding {

    /**
     * Tests that CharRange.isIn normalises reversed endpoints: when 'Z' (start) > 'W' (end),
     * the constructor swaps them so that getStart() returns 'W' and getEnd() returns 'Z'.
     * Also verifies the range is non-negated and that forEach can traverse its characters.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        // 'Z' > 'W', so the constructor normalises the order to [W..Z]
        CharRange reversedInputRange = CharRange.isIn('Z', 'W');

        assertFalse("Range created with isIn should not be negated", reversedInputRange.isNegated());

        // forEach must visit each character in [W, X, Y, Z]; a mock consumer accepts without side effects
        Consumer<Character> mockCharConsumer = (Consumer<Character>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        reversedInputRange.forEach(mockCharConsumer);

        // After normalisation the lower bound is 'W' and the upper bound is 'Z'
        assertEquals('W', reversedInputRange.getStart());
        assertEquals('Z', reversedInputRange.getEnd());
    }
}
