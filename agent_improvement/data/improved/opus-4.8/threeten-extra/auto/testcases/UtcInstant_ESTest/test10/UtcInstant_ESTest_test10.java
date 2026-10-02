package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test10 extends UtcInstant_ESTest_scaffolding {

    /**
     * An instant is never before itself: {@code isBefore} comparing a value
     * against the same value must return {@code false}.
     */
    @Test(timeout = 4000)
    public void isBefore_returnsFalse_whenComparedWithItself() throws Throwable {
        // Build a UtcInstant three seconds after the Unix epoch (1970-01-01T00:00:03Z).
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        boolean isBeforeItself = utcInstant.isBefore(utcInstant);

        // The Unix epoch falls on Modified Julian Day 40587, and three seconds
        // into the day equals 3,000,000,000 nanoseconds.
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
        assertEquals(3_000_000_000L, utcInstant.getNanoOfDay());
        assertFalse(isBeforeItself);
    }
}
