package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test00 extends Weeks_ESTest_scaffolding {

    /**
     * Negating a {@code Weeks} flips the sign of its amount, and negating twice
     * returns to the original value. This verifies that round-trip negation
     * restores both the amount and equality with the starting instance.
     */
    @Test(timeout = 4000)
    public void doubleNegationRestoresOriginalWeeks() throws Throwable {
        Weeks minus1075Weeks = Weeks.of(-1075);

        Weeks negatedOnce = minus1075Weeks.negated();
        Weeks negatedTwice = negatedOnce.negated();

        // First negation flips -1075 to +1075.
        assertEquals(1075, negatedOnce.getAmount());

        // Second negation flips +1075 back to -1075.
        assertEquals(-1075, negatedTwice.getAmount());

        // Double negation yields a value equal to the original.
        assertTrue(negatedTwice.equals(minus1075Weeks));

        // The intermediate (+1075) result differs from both -1075 instances.
        assertFalse(negatedTwice.equals(negatedOnce));
        assertFalse(negatedOnce.equals(minus1075Weeks));
    }
}
