package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test09 extends AccountingChronology_ESTest_scaffolding {

    /**
     * {@link AccountingChronology#localDateTime(java.time.temporal.TemporalAccessor)} requires the
     * temporal to expose a time-of-day. An {@link AccountingDate} carries only a date, so the
     * conversion must fail with a {@link DateTimeException} raised from {@code java.time.chrono.Chronology}.
     */
    @Test(timeout = 4000)
    public void localDateTime_fromDateOnlyTemporal_throwsDateTimeException() throws Throwable {
        // Build an accounting chronology whose years end on a Wednesday at the end of March.
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.MARCH,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS,
                4,
                1461);

        // A date-only temporal: it has no time-of-day fields.
        AccountingDate dateOnly = AccountingDate.now(chronology);

        try {
            chronology.localDateTime(dateOnly);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Unable to obtain ChronoLocalDateTime from TemporalAccessor:
            // class org.threeten.extra.chrono.AccountingDate
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
