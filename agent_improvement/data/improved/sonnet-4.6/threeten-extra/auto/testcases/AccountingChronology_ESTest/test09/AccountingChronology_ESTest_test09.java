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
public class AccountingChronology_ESTest_test09 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that passing a date-only AccountingDate to localDateTime() throws
     * DateTimeException, because AccountingDate lacks time-of-day information required
     * to construct a ChronoLocalDateTime.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.MARCH,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS,
                4,
                1461);

        AccountingDate dateOnly = AccountingDate.now(chronology);

        // AccountingDate is a date-only TemporalAccessor; it cannot be converted to a
        // ChronoLocalDateTime because it carries no time-of-day component.
        try {
            chronology.localDateTime(dateOnly);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Unable to obtain ChronoLocalDateTime from TemporalAccessor: class org.threeten.extra.chrono.AccountingDate
            //
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
