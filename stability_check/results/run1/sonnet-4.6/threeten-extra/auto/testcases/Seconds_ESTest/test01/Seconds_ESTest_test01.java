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
        // 396 hours = 396 * 3600 = 1,425,600 seconds
        Seconds secondsFrom396Hours = Seconds.ofHours(396);
        // A Duration of 396 seconds to subtract
        Duration durationOf396Seconds = Duration.ofSeconds((long) 396);

        // Subtracting 396 seconds from 1,425,600 seconds yields 1,425,204 seconds
        Seconds secondsAfterSubtraction = secondsFrom396Hours.minus((TemporalAmount) durationOf396Seconds);

        boolean originalEqualsReduced = secondsFrom396Hours.equals(secondsAfterSubtraction);

        // The subtraction reduces the amount, so the two Seconds instances differ
        assertEquals(1425204, secondsAfterSubtraction.getAmount());
        assertFalse(secondsAfterSubtraction.equals((Object) secondsFrom396Hours));
        assertFalse(originalEqualsReduced);

        // The original Seconds instance is immutable and remains unchanged
        assertEquals(1425600, secondsFrom396Hours.getAmount());
    }
}
