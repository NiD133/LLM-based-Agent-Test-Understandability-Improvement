package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test04 extends Days_ESTest_scaffolding {

    /**
     * Verifies that subtracting a negative Duration from a zero-day Days instance
     * yields a positive day count, and that subtractFrom produces a new Temporal object.
     *
     * Steps:
     *  1. Create a reference instant (same start and end for Days.between -> 0 days).
     *  2. Subtract a negative duration (-2086 days), which results in +2086 days.
     *  3. Subtract those 2086 days from the reference instant via subtractFrom.
     *  4. Assert the adjusted temporal is a distinct object from the original instant.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // An instant used as both start and end — Days.between same-point yields zero days
        Instant referenceInstant = MockInstant.ofEpochSecond((-2086L), (-2086L));
        Days zeroDays = Days.between(referenceInstant, referenceInstant);

        // Subtracting a negative duration (-2086 days) flips the sign: 0 - (-2086) = +2086
        Duration negativeDuration = Duration.ofDays((-2086L));
        Days daysAfterSubtraction = zeroDays.minus((TemporalAmount) negativeDuration);

        // subtractFrom returns a new Temporal shifted back by 2086 days
        Temporal adjustedTemporal = daysAfterSubtraction.subtractFrom(referenceInstant);

        assertEquals(2086, daysAfterSubtraction.getAmount());
        assertNotSame(adjustedTemporal, referenceInstant);
        assertEquals(0, zeroDays.getAmount());
    }
}
