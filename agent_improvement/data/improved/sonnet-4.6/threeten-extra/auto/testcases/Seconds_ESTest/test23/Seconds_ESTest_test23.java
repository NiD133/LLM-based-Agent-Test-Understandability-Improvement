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

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        // -1 minute converts to -60 seconds
        Seconds negativeOneMinute = Seconds.ofMinutes(-1);

        // Subtracting a Seconds amount from itself should yield zero;
        // the original instance remains unchanged (Seconds is immutable)
        Seconds result = negativeOneMinute.minus((TemporalAmount) negativeOneMinute);

        assertEquals(-60, negativeOneMinute.getAmount());
        assertEquals(0, result.getAmount());
    }
}
