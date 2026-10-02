package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test18 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that proleptic year 0 is reported as a non-leap year for an
     * accounting chronology whose years end on the last Tuesday of January
     * using the 4-5-4 week quarter pattern.
     */
    @Test(timeout = 4000)
    public void yearZeroIsNotLeapYear() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.JANUARY,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                9,
                0);

        boolean isLeapYear = chronology.isLeapYear(0);

        assertFalse(isLeapYear);
    }
}
