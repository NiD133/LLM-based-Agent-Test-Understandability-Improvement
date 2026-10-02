package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test17 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that creating Seconds from a negative minute count yields a negative
     * (non-zero) total, with the correct seconds value (-1 minute = -60 seconds).
     */
    @Test(timeout = 4000)
    public void test_ofNegativeMinutes_isNotZeroAndEqualsMinusSixty() throws Throwable {
        Seconds negativeOneMinute = Seconds.ofMinutes(-1);

        boolean isZero = negativeOneMinute.isZero();
        assertFalse("Seconds from -1 minute should not be zero", isZero);
        assertEquals("Expected -1 minute to equal -60 seconds", -60, negativeOneMinute.getAmount());
    }
}
