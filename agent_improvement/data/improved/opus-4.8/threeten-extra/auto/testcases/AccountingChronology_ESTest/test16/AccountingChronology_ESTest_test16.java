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
public class AccountingChronology_ESTest_test16 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can report the valid value range for the
     * ALIGNED_WEEK_OF_MONTH field. The chronology is built with a standard
     * 4-5-4-week quarter division, and range(...) is expected to return a non-null range.
     */
    @Test(timeout = 4000)
    public void range_forAlignedWeekOfMonth_returnsNonNullRange() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.FRIDAY,
                Month.FEBRUARY,
                false,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                4,
                4);

        ValueRange alignedWeekOfMonthRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);

        assertNotNull(alignedWeekOfMonthRange);
    }
}
