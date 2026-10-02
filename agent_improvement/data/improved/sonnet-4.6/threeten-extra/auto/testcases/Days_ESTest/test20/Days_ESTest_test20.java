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
public class Days_ESTest_test20 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofWeeks_convertsToTotalDays_andGetOnDaysOneReturnsOne() throws Throwable {
        // -4447 weeks * 7 days/week = -31129 days
        Days daysFromNegativeWeeks = Days.ofWeeks(-4447);

        // MILLI_OF_DAY's range unit is DAYS; use it to query Days.ONE
        ChronoField milliOfDayField = ChronoField.MILLI_OF_DAY;
        TemporalUnit rangeUnitOfMilliOfDay = milliOfDayField.getRangeUnit();
        long oneDayAmountInDaysUnit = daysFromNegativeWeeks.ONE.get(rangeUnitOfMilliOfDay);

        assertEquals((-31129), daysFromNegativeWeeks.getAmount());
        assertEquals(1L, oneDayAmountInDaysUnit);
    }
}
