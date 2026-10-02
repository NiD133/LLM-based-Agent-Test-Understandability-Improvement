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
public class CharRange_ESTest_test23 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that isNotIn() with out-of-order arguments reverses start/end,
     * marks the range as negated, and allows forEach iteration without side effects.
     *
     * '￸' > 'T', so the constructor normalizes the range to [start='T', end='￸'].
     * The resulting negated range represents every character NOT between 'T' and '￸'.
     */
    @Test(timeout = 4000)
    public void testIsNotInWithReversedBoundsNormalizesStartEndAndIteratesNegatedRange() throws Throwable {
        // Construct a negated CharRange with reversed bounds: '￸' > 'T', so they are swapped internally
        CharRange negatedRange = CharRange.isNotIn('￸', 'T');

        // The range must be negated because isNotIn() was used
        assertTrue(negatedRange.isNegated());

        // Use a no-op mock consumer to exercise forEach without inspecting each character
        Consumer<Object> noOpConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        negatedRange.forEach(noOpConsumer);

        // After construction the bounds are reordered: the smaller char 'T' becomes start,
        // and the larger '￸' becomes end
        assertEquals('T', negatedRange.getStart());
        assertEquals('￸', negatedRange.getEnd());
    }
}
