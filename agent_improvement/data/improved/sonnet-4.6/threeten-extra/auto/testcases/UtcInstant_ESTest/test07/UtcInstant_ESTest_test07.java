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
public class UtcInstant_ESTest_test07 extends UtcInstant_ESTest_scaffolding {

    // MJD for the Unix epoch (1970-01-01); UTC epoch is defined relative to this day
    private static final long MJD_UNIX_EPOCH = 40587L;

    // 3 seconds after midnight expressed in nanoseconds (3 * 1_000_000_000)
    private static final long THREE_SECONDS_IN_NANOS = 3_000_000_000L;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Create a standard Java Instant 3 seconds into the Unix epoch (1970-01-01T00:00:03Z)
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);

        // Convert to a UtcInstant, which maps the same point in time to MJD + nano-of-day
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        // An instance must be equal to itself (reflexive equality)
        boolean isEqualToItself = utcInstant.equals(utcInstant);
        assertTrue(isEqualToItself);

        // 1970-01-01 corresponds to MJD 40587
        assertEquals(MJD_UNIX_EPOCH, utcInstant.getModifiedJulianDay());

        // 3 seconds past midnight = 3,000,000,000 nanoseconds into the day
        assertEquals(THREE_SECONDS_IN_NANOS, utcInstant.getNanoOfDay());
    }
}
