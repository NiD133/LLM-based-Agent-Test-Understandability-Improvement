package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test31 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that Minutes.between() returns zero minutes when both the start
     * and end temporal are the same instant, that getUnits() returns the
     * supported units without error, and that getAmount() reports 0.
     */
    @Test(timeout = 4000)
    public void test_betweenSameInstant_returnsZeroMinutes() throws Throwable {
        // Use a mocked OffsetDateTime so the test is deterministic
        OffsetDateTime sameInstant = MockOffsetDateTime.now();

        // Computing the span between an instant and itself should be zero minutes
        Minutes minutesBetween = Minutes.between(sameInstant, sameInstant);

        // getUnits() must not throw and returns the list of supported temporal units
        minutesBetween.getUnits();

        // The elapsed time between the same two instants is exactly zero
        assertEquals(0, minutesBetween.getAmount());
    }
}
