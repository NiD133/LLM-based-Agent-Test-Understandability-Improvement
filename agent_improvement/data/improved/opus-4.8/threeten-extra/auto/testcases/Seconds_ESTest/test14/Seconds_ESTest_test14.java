package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test14 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies a chain of arithmetic operations on {@link Seconds}:
     * dividing a negative amount and then subtracting the original amount.
     */
    @Test(timeout = 4000)
    public void subtractingNegativeAmountFromQuotientYieldsPositiveResult() throws Throwable {
        // -19 minutes converts to -19 * 60 = -1140 seconds.
        Seconds negativeMinutes = Seconds.ofMinutes(-19);
        assertEquals(-1140, negativeMinutes.getAmount());

        // Integer division: -1140 / 9 = -126 (truncated towards zero).
        Seconds quotient = negativeMinutes.dividedBy(9);
        assertFalse(quotient.isPositive());

        // Subtracting the original negative amount: -126 - (-1140) = 1014.
        Seconds difference = quotient.minus((TemporalAmount) negativeMinutes);
        assertEquals(1014, difference.getAmount());
        assertTrue(difference.isPositive());
    }
}
