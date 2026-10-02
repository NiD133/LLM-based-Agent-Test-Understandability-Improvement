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
public class Minutes_ESTest_test22 extends Minutes_ESTest_scaffolding {

    private static final int INITIAL_MINUTES = -946;
    private static final int DAYS_TO_SUBTRACT = -946;
    private static final int EXPECTED_INITIAL_MINUTES = -946;
    private static final int EXPECTED_MINUTES_AFTER_SUBTRACTING_PERIOD = 1361294;

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Minutes initialAmount = Minutes.of(INITIAL_MINUTES);
        Period periodToSubtract = Period.ofDays(DAYS_TO_SUBTRACT);

        Minutes result = initialAmount.minus((TemporalAmount) periodToSubtract);

        assertEquals(EXPECTED_INITIAL_MINUTES, initialAmount.getAmount());
        assertEquals(EXPECTED_MINUTES_AFTER_SUBTRACTING_PERIOD, result.getAmount());
    }
}
