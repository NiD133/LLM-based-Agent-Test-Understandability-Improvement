package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test11 extends UtcInstant_ESTest_scaffolding {

    /**
     * Verifies that moving a {@link UtcInstant} to an earlier Modified Julian Day,
     * while keeping its nano-of-day unchanged, produces an earlier instant.
     */
    @Test(timeout = 4000)
    public void withEarlierModifiedJulianDay_yieldsEarlierInstant() throws Throwable {
        // Start from a TAI instant just before the TAI epoch and convert it to UTC.
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(-24L, -24L);
        UtcInstant originalInstant = taiInstant.toUtcInstant();

        // Copy the instant onto an earlier Modified Julian Day, keeping the nano-of-day.
        UtcInstant earlierDayInstant = originalInstant.withModifiedJulianDay(-24L);

        // The original (later day) instant must be after the one on the earlier day.
        boolean originalIsAfterEarlier = originalInstant.isAfter(earlierDayInstant);

        assertEquals(-24L, earlierDayInstant.getModifiedJulianDay());
        assertTrue(originalIsAfterEarlier);
        // withModifiedJulianDay preserves the nano-of-day from the original instant.
        assertEquals(86365999999976L, originalInstant.getNanoOfDay());
    }
}
