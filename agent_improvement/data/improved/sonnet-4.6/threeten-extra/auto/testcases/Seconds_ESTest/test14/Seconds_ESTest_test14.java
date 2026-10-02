package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test14 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // -19 minutes = -1140 seconds
        Seconds negativeMinutes = Seconds.ofMinutes(-19);

        // Integer division: -1140 / 9 = -126 (truncated toward zero)
        Seconds dividedSeconds = negativeMinutes.dividedBy(9);

        // Subtracting a negative amount: -126 - (-1140) = 1014
        Seconds result = dividedSeconds.minus((TemporalAmount) negativeMinutes);

        boolean isResultPositive = result.isPositive();

        assertEquals(1014, result.getAmount());
        assertTrue(isResultPositive);
        assertFalse(dividedSeconds.isPositive()); // -126 is not positive
        assertEquals((-1140), negativeMinutes.getAmount());
    }
}
