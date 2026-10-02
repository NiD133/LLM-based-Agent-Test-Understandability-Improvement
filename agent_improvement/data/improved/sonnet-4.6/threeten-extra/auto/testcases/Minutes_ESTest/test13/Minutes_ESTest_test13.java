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
public class Minutes_ESTest_test13 extends Minutes_ESTest_scaffolding {

    // 53038 hours * 60 minutes/hour = 3182280 minutes
    private static final int INPUT_HOURS = 53038;
    private static final int EXPECTED_MINUTES = 3182280;

    @Test(timeout = 4000)
    public void testOfHoursProducesPositiveMinuteAmount() throws Throwable {
        Minutes minutes = Minutes.ofHours(INPUT_HOURS);

        assertTrue(minutes.isPositive());
        assertEquals(EXPECTED_MINUTES, minutes.getAmount());
    }
}
