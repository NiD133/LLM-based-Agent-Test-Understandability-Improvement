package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test08 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@link Seconds#abs()} returns the positive equivalent of a
     * negative amount, while leaving the original instance unchanged.
     */
    @Test(timeout = 4000)
    public void abs_negativeMinutes_returnsPositiveSeconds() throws Throwable {
        // -1 minute is stored as -60 seconds.
        Seconds negativeMinute = Seconds.ofMinutes(-1);

        Seconds absoluteValue = negativeMinute.abs();

        assertEquals("original amount stays negative", -60, negativeMinute.getAmount());
        assertEquals("abs() yields the positive amount", 60, absoluteValue.getAmount());
    }
}
