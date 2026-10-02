package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test23 extends Seconds_ESTest_scaffolding {

    /**
     * Subtracting a Seconds amount from itself yields zero,
     * while the original amount remains unchanged (immutability).
     */
    @Test(timeout = 4000)
    public void subtractingValueFromItselfGivesZero() throws Throwable {
        // -1 minute is stored as -60 seconds.
        Seconds negativeOneMinute = Seconds.ofMinutes(-1);

        Seconds difference = negativeOneMinute.minus((TemporalAmount) negativeOneMinute);

        assertEquals("-1 minute should equal -60 seconds", -60, negativeOneMinute.getAmount());
        assertEquals("an amount minus itself should be zero", 0, difference.getAmount());
    }
}
