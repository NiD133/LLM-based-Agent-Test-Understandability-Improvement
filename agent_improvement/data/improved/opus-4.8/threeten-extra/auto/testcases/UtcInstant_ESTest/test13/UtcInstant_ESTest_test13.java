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
public class UtcInstant_ESTest_test13 extends UtcInstant_ESTest_scaffolding {

    /**
     * Converting an Instant that is 3 seconds after the 1970 epoch should yield a
     * UtcInstant on the epoch day (Modified Julian Day 40587) at 3 seconds into the
     * day, and that instant is not a leap second.
     */
    @Test(timeout = 4000)
    public void of_instantThreeSecondsAfterEpoch_mapsToEpochDayAndIsNotLeapSecond() throws Throwable {
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);

        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        boolean isLeapSecond = utcInstant.isLeapSecond();

        // MJD 40587 corresponds to 1970-01-01, the Unix epoch day.
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
        // 3 seconds into the day, expressed in nanoseconds.
        assertEquals(3_000_000_000L, utcInstant.getNanoOfDay());
        assertFalse(isLeapSecond);
    }
}
