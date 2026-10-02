package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test13 extends UtcInstant_ESTest_scaffolding {

    // MJD for 1970-01-01 (Unix epoch); day 0 of MJD is 1858-11-17
    private static final long MJD_UNIX_EPOCH = 40587L;

    // 3 seconds expressed in nanoseconds: 3 * 1_000_000_000
    private static final long THREE_SECONDS_IN_NANOS = 3_000_000_000L;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Create a standard Java Instant 3 seconds after the Unix epoch (1970-01-01T00:00:03Z)
        Instant javaInstant = MockInstant.ofEpochSecond(3L);

        // Convert to UtcInstant, which uses Modified Julian Day + nanosecond-of-day representation
        UtcInstant utcInstant = UtcInstant.of(javaInstant);

        // 3 seconds after midnight is not a leap second (leap seconds occur at 23:59:60)
        boolean isLeapSecond = utcInstant.isLeapSecond();
        assertFalse(isLeapSecond);

        // The instant falls on the same calendar day (MJD 40587 = 1970-01-01)
        assertEquals(MJD_UNIX_EPOCH, utcInstant.getModifiedJulianDay());

        // The nanosecond-of-day is exactly 3 seconds worth of nanoseconds from start of day
        assertEquals(THREE_SECONDS_IN_NANOS, utcInstant.getNanoOfDay());
    }
}
