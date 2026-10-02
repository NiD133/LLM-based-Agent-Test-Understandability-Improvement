package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test12 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that adding a negative duration to a UtcInstant at midnight (nanoOfDay=0)
     * correctly wraps backwards into the previous day.
     *
     * MJD -1194 at midnight + (-3113 ms) shifts 3,113,000,000 ns before midnight,
     * yielding nanoOfDay = 86,400,000,000,000 - 3,113,000,000 = 86,396,887,000,000.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Duration negativeOffset = Duration.ofMillis(-3113L);
        UtcInstant midnightOnMjdNeg1194 = UtcInstant.ofModifiedJulianDay(-1194L, 0);

        UtcInstant resultInstant = midnightOnMjdNeg1194.plus(negativeOffset);

        assertEquals(86396887000000L, resultInstant.getNanoOfDay());
    }
}
