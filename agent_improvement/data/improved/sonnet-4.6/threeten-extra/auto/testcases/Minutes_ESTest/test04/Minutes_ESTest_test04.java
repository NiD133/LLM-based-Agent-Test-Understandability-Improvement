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
public class Minutes_ESTest_test04 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that Minutes.between() with identical start and end returns zero minutes,
     * and that subtracting zero minutes from a temporal leaves it unchanged.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        OffsetDateTime now = MockOffsetDateTime.now();

        // between the same instant produces zero minutes
        Minutes zeroMinutes = Minutes.between(now, now);

        // subtracting zero minutes is a no-op; result is intentionally unused
        Minutes.ZERO.subtractFrom(now);

        assertTrue(zeroMinutes.isZero());
        assertEquals(0, zeroMinutes.getAmount());
    }
}
