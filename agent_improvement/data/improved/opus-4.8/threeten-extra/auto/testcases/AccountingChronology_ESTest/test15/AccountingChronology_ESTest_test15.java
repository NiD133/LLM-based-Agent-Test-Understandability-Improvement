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
public class AccountingChronology_ESTest_test15 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can report the valid value range
     * for the ALIGNED_WEEK_OF_YEAR field.
     */
    @Test(timeout = 4000)
    public void rangeForAlignedWeekOfYearIsNotNull() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.APRIL,
                /* inLastWeek */ true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                /* leapWeekInMonth */ 2,
                /* yearOffset */ 2);

        ValueRange alignedWeekOfYearRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);

        assertNotNull(alignedWeekOfYearRange);
    }
}
