package org.threeten.extra.chrono;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test30 extends AccountingChronology_ESTest_scaffolding {

    /**
     * A bare {@link Month} does not carry the date/time/zone fields required to build a
     * zoned date-time, so {@link AccountingChronology#zonedDateTime(TemporalAccessor)}
     * is expected to fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromMonthThrowsDateTimeException() throws Throwable {
        AccountingChronology accountingChronology = AccountingChronology.create(
                DayOfWeek.SUNDAY,
                Month.JANUARY,
                false,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                12,
                12);

        TemporalAccessor insufficientTemporal = Month.JANUARY;

        try {
            accountingChronology.zonedDateTime(insufficientTemporal);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Unable to obtain ChronoZonedDateTime from TemporalAccessor: class java.time.Month
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
