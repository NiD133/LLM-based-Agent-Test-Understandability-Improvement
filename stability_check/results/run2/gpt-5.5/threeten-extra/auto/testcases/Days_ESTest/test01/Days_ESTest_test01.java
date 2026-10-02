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
public class Days_ESTest_test01 extends Days_ESTest_scaffolding {

    private static final int NEGATIVE_WEEK_COUNT = -3386;
    private static final int EXPECTED_MULTIPLIED_DAYS = 80254972;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Days negativeDaysFromWeeks = Days.ofWeeks(NEGATIVE_WEEK_COUNT);
        Days multipliedDays = negativeDaysFromWeeks.multipliedBy(NEGATIVE_WEEK_COUNT);

        boolean originalEqualsMultiplied = negativeDaysFromWeeks.equals(multipliedDays);

        assertFalse(originalEqualsMultiplied);
        assertFalse(multipliedDays.equals((Object) negativeDaysFromWeeks));
        assertFalse(negativeDaysFromWeeks.isPositive());
        assertEquals(EXPECTED_MULTIPLIED_DAYS, multipliedDays.getAmount());
    }
}
