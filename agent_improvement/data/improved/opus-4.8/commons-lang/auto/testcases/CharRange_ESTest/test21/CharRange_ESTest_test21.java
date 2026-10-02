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
     * When {@link CharRange#isIn(char, char)} is given endpoints in descending
     * order ('Z' before 'W'), the constructor swaps them so the range always runs
     * from the lower character to the higher one. The resulting range is therefore
     * a normal (non-negated) range whose start is 'W' and end is 'Z'.
     *
     * <p>Iterating the range via {@link CharRange#forEach} should complete without
     * error, here exercised with a mock {@link Consumer}.</p>
     */
    @Test(timeout = 4000)
    public void isInWithReversedEndpointsSwapsStartAndEnd() throws Throwable {
        // Endpoints are passed high-then-low; the range should normalize them.
        CharRange descendingRange = CharRange.isIn('Z', 'W');

        // isIn produces a plain (non-negated) range.
        assertFalse(descendingRange.isNegated());

        // Walking the range with forEach must not throw.
        Consumer<Character> characterConsumer =
                (Consumer<Character>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        descendingRange.forEach(characterConsumer);

        // Endpoints are stored in ascending order regardless of input order.
        assertEquals('W', descendingRange.getStart());
        assertEquals('Z', descendingRange.getEnd());
    }
}
