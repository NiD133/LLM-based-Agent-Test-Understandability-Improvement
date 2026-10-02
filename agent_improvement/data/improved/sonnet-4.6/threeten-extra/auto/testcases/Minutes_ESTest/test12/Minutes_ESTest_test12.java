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
public class Minutes_ESTest_test12 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // 327 hours = 19620 minutes
        Minutes baseMinutes = Minutes.ofHours(327);

        // Adding 327 minutes produces a new instance: 19620 + 327 = 19947
        Minutes minutesAfterAdd = baseMinutes.plus(327);

        boolean minutesAfterAddEqualsBase = minutesAfterAdd.equals(baseMinutes);

        // The two instances should not be equal since their amounts differ
        assertFalse(minutesAfterAddEqualsBase);
        assertFalse(baseMinutes.equals((Object) minutesAfterAdd));

        // Verify the exact total after addition
        assertEquals(19947, minutesAfterAdd.getAmount());
    }
}
