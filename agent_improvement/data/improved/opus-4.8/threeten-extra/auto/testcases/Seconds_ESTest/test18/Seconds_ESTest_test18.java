package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test18 extends Seconds_ESTest_scaffolding {

    /**
     * Seconds.ofMinutes(-1) should hold -60 seconds (one minute = 60 seconds)
     * and report itself as negative.
     */
    @Test(timeout = 4000)
    public void ofMinutes_withNegativeOneMinute_isNegativeSixtySeconds() throws Throwable {
        Seconds negativeOneMinute = Seconds.ofMinutes(-1);

        int secondsAmount = negativeOneMinute.getAmount();
        boolean isNegative = negativeOneMinute.isNegative();

        assertEquals("one negative minute is -60 seconds", -60, secondsAmount);
        assertTrue("an amount of -60 seconds is negative", isNegative);
    }
}
