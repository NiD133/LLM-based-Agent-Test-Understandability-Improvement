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
     * An accounting year that ends in the last Tuesday of January, using 4-5-4 week
     * quarters with the leap week placed in month 9, should not report proleptic
     * year 0 as a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYearReturnsFalseForYearZero() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.JANUARY,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                9,
                0);

        boolean leapYear = chronology.isLeapYear(0L);

        assertFalse(leapYear);
    }
}
