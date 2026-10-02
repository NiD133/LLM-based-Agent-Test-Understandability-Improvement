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
public class Minutes_ESTest_test00 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final int hours = 327;
        final int minutesToSubtract = 327;
        final int minutesToAddBack = 327;
        final int expectedMinutesInHours = 19620;

        Minutes originalDuration = Minutes.ofHours(hours);
        Minutes reducedDuration = originalDuration.minus(minutesToSubtract);
        Minutes restoredDuration = reducedDuration.plus(minutesToAddBack);

        boolean restoredEqualsOriginal = restoredDuration.equals(originalDuration);

        assertFalse(restoredDuration.equals((Object) reducedDuration));
        assertEquals(expectedMinutesInHours, restoredDuration.getAmount());
        assertTrue(restoredEqualsOriginal);
        assertFalse(reducedDuration.equals((Object) originalDuration));
    }
}
