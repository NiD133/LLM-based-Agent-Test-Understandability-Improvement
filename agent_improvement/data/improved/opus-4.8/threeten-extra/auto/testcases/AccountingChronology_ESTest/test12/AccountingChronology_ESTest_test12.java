package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test12 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link AccountingChronology#range(ChronoField)} returns a
     * (non-null) value range for the PROLEPTIC_MONTH field.
     */
    @Test(timeout = 4000)
    public void rangeForProlepticMonthReturnsValueRange() throws Throwable {
        // Build an accounting chronology whose years end on a Tuesday nearest the
        // end of January, divided into 4-5-4 week quarters with the leap week in month 1.
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.JANUARY,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                1,
                1);

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
