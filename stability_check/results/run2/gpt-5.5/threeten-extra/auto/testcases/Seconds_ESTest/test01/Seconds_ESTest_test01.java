package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test01 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Seconds secondsFor396Hours = Seconds.ofHours(396);
        Duration durationOf396Seconds = Duration.ofSeconds((long) 396);

        Seconds afterSubtracting396Seconds = secondsFor396Hours.minus((TemporalAmount) durationOf396Seconds);
        boolean originalEqualsReduced = secondsFor396Hours.equals(afterSubtracting396Seconds);

        assertEquals(1425204, afterSubtracting396Seconds.getAmount());
        assertFalse(afterSubtracting396Seconds.equals((Object) secondsFor396Hours));
        assertFalse(originalEqualsReduced);
        assertEquals(1425600, secondsFor396Hours.getAmount());
    }
}
