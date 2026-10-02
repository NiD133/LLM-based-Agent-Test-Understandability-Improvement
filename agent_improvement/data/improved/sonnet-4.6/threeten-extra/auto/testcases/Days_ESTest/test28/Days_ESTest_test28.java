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
public class Days_ESTest_test28 extends Days_ESTest_scaffolding {

    // -25018 weeks * 7 days/week = -175126 days
    private static final int WEEKS_INPUT = -25018;
    private static final int EXPECTED_DAYS = -175126;

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Days daysFromNegativeWeeks = Days.ofWeeks(WEEKS_INPUT);

        // getUnits() must return the supported temporal units without throwing
        daysFromNegativeWeeks.getUnits();

        assertEquals(EXPECTED_DAYS, daysFromNegativeWeeks.getAmount());
    }
}
