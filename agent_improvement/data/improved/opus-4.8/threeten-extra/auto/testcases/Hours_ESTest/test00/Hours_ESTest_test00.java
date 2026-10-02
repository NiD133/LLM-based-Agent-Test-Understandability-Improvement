package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test00 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that {@link Hours#negated()} flips the sign of the amount and that
     * negating the same value twice yields equal-but-distinct {@code Hours} instances.
     */
    @Test(timeout = 4000)
    public void negatingNegativeHoursProducesEqualPositiveResults() throws Throwable {
        Hours negativeHours = Hours.of(-4696);

        // Negating the same source twice produces two separate positive instances.
        Hours firstNegation = negativeHours.negated();
        Hours secondNegation = negativeHours.negated();

        // Both negations represent +4696 hours, so they are equal to each other.
        assertTrue(firstNegation.equals(secondNegation));
        assertEquals(4696, secondNegation.getAmount());

        // The negated instances differ from the original negative value.
        assertFalse(negativeHours.equals((Object) firstNegation));
        assertFalse(secondNegation.equals((Object) negativeHours));
        assertEquals(-4696, negativeHours.getAmount());
    }
}
