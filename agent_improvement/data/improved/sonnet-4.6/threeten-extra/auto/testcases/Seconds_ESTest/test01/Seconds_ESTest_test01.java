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
    public void testMinusDurationFromHourBasedSecondsProducesCorrectAmountAndInequality() throws Throwable {
        // 396 hours expressed as Seconds (396 * 3600 = 1,425,600 seconds)
        Seconds hourBasedSeconds = Seconds.ofHours(396);

        // A Duration of 396 seconds to subtract
        Duration durationToSubtract = Duration.ofSeconds((long) 396);

        // Subtract the Duration from the hour-based Seconds
        Seconds resultAfterSubtraction = hourBasedSeconds.minus((TemporalAmount) durationToSubtract);

        // Verify the subtraction result has the expected amount (1,425,600 - 396 = 1,425,204)
        assertEquals(1425204, resultAfterSubtraction.getAmount());

        // Verify that the original is not equal to the result
        boolean originalEqualsResult = hourBasedSeconds.equals(resultAfterSubtraction);
        assertFalse(originalEqualsResult);

        // Verify equals is symmetric: result also does not equal the original
        assertFalse(resultAfterSubtraction.equals((Object) hourBasedSeconds));

        // Verify the original Seconds value remains unchanged (immutability check)
        assertEquals(1425600, hourBasedSeconds.getAmount());
    }
}
