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

    // 1970-01-01 (Unix epoch date) expressed as a Modified Julian Day
    private static final long MJD_UNIX_EPOCH_DATE = 40587L;

    // 3 seconds after midnight expressed in nanoseconds
    private static final long THREE_SECONDS_IN_NANOS = 3_000_000_000L;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Build a UtcInstant representing 1970-01-01T00:00:03Z (3 s after Unix epoch)
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        // An instant is never strictly before itself
        boolean isBeforeItself = utcInstant.isBefore(utcInstant);
        assertFalse(isBeforeItself);

        // 3 s into the day = 3 * 1,000,000,000 ns from midnight
        assertEquals(THREE_SECONDS_IN_NANOS, utcInstant.getNanoOfDay());

        // Still on the same calendar day (MJD 40587 = 1970-01-01)
        assertEquals(MJD_UNIX_EPOCH_DATE, utcInstant.getModifiedJulianDay());
    }
}
