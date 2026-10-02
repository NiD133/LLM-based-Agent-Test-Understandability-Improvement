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
public class Days_ESTest_test26 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Same start and end instant, so the interval spans zero days
        Instant sameInstant = MockInstant.ofEpochSecond((-2086L), (-2086L));
        Days zeroDays = Days.between(sameInstant, sameInstant);

        // Subtracting a negative duration of 2086 days from zero yields +2086 days
        Duration negativeDuration = Duration.ofDays((-2086L));
        Days positiveDays = zeroDays.minus((TemporalAmount) negativeDuration);

        // Adding 2086 days to itself produces 4172 days
        Days doubledDays = positiveDays.plus((TemporalAmount) positiveDays);
        assertEquals(4172, doubledDays.getAmount());

        // The original zero-day interval is unchanged
        assertTrue(zeroDays.isZero());
    }
}
