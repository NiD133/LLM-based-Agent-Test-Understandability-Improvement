package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test03 extends MutableClock_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        MutableClock utcEpochClock = MutableClock.epochUTC();
        ZoneOffset minimumZoneOffset = ZoneOffset.MIN;

        MutableClock firstMinimumZoneClock = utcEpochClock.withZone(minimumZoneOffset);
        MutableClock secondMinimumZoneClock = utcEpochClock.withZone(minimumZoneOffset);

        boolean clocksShareStateAndZone = firstMinimumZoneClock.equals(secondMinimumZoneClock);

        assertTrue(clocksShareStateAndZone);
        assertFalse(secondMinimumZoneClock.equals((Object) utcEpochClock));
        assertNotSame(secondMinimumZoneClock, utcEpochClock);
    }
}
