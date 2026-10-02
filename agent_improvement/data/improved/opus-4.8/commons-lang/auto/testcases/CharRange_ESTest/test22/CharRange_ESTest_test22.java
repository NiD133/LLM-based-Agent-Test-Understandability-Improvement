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
public class CharRange_ESTest_test22 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that iterating a negated range with {@link CharRange#forEach}
     * leaves the range's defining start/end characters unchanged.
     */
    @Test(timeout = 4000)
    public void forEachDoesNotAlterRangeBounds() throws Throwable {
        // A negated range covering everything except 'T'..'￿'
        // (￿ is Character.MAX_VALUE, the largest possible char).
        CharRange negatedRange = CharRange.isNotIn('T', '￿');

        // Iterate over every character in the range; the consumer's behaviour
        // is irrelevant to the bounds, so a mock stand-in is used.
        Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        negatedRange.forEach(consumer);

        // The start and end characters remain exactly as constructed.
        assertEquals('T', negatedRange.getStart());
        assertEquals('￿', negatedRange.getEnd());
    }
}
