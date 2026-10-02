package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test30 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that zonedDateTime(TemporalAccessor) throws DateTimeException when
     * the accessor is a Month, which lacks zone and time-of-day information
     * required to produce a ChronoZonedDateTime.
     */
    @Test(timeout = 4000)
    public void test30() throws Throwable {
        // Build an accounting chronology: years end on Sunday nearest end of January,
        // divided into quarters (4-4-5 pattern), with the leap week in month 12.
        AccountingYearDivision quarterPattern = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;
        DayOfWeek yearEndDay = DayOfWeek.SUNDAY;
        Month yearEndMonth = Month.JANUARY;
        AccountingChronology chronology = AccountingChronology.create(yearEndDay, yearEndMonth, false, quarterPattern, 12, 12);

        // Month is a TemporalAccessor but does not carry zone or time information,
        // so converting it to a ChronoZonedDateTime must fail with DateTimeException.
        try {
            chronology.zonedDateTime((TemporalAccessor) yearEndMonth);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Unable to obtain ChronoZonedDateTime from TemporalAccessor: class java.time.Month
            //
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
