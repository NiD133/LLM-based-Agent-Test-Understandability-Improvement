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
     * Verifies that {@link CharRange#isNotIn(char, char)} builds a negated range
     * and that, when the endpoints are supplied in the wrong order, the constructor
     * swaps them so that {@code start <= end}.
     *
     * <p>Here {@code '￸'} (0xFFF8) is greater than {@code 'T'} (0x54), so the
     * resulting range reports {@code 'T'} as its start and {@code '￸'} as its
     * end. The range is also iterated via {@link CharRange#forEach} to confirm the
     * traversal completes without error.</p>
     */
    @Test(timeout = 4000)
    public void testIsNotInSwapsReversedEndpointsAndIsNegated() throws Throwable {
        final char higherChar = '￸';
        final char lowerChar = 'T';

        CharRange negatedRange = CharRange.isNotIn(higherChar, lowerChar);

        assertTrue("isNotIn must produce a negated range", negatedRange.isNegated());

        // A mock consumer is enough: we only need forEach to run to completion.
        Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        negatedRange.forEach(consumer);

        // Endpoints were given reversed, so the constructor swaps them.
        assertEquals("start should be the lower of the two endpoints", lowerChar, negatedRange.getStart());
        assertEquals("end should be the higher of the two endpoints", higherChar, negatedRange.getEnd());
    }
}
