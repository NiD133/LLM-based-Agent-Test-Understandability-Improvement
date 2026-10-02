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

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        final char requestedStart = 'Z';
        final char requestedEnd = 'W';
        final char normalizedStart = 'W';
        final char normalizedEnd = 'Z';

        CharRange rangeWithReversedBounds = CharRange.isIn(requestedStart, requestedEnd);

        assertFalse(rangeWithReversedBounds.isNegated());

        Consumer<Character> visitedCharacterConsumer =
                (Consumer<Character>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        rangeWithReversedBounds.forEach(visitedCharacterConsumer);

        assertEquals(normalizedStart, rangeWithReversedBounds.getStart());
        assertEquals(normalizedEnd, rangeWithReversedBounds.getEnd());
    }
}
