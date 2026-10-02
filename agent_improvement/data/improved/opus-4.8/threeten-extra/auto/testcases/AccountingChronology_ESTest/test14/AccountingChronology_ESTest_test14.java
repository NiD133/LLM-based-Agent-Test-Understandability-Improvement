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
public class AccountingChronology_ESTest_test14 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can report the valid value range for
     * the DAY_OF_MONTH field, returning a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfMonthReturnsNonNullRange() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.JUNE,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                10,
                -1);

        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);

        assertNotNull(dayOfMonthRange);
    }
}
