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
public class AccountingChronology_ESTest_test19 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that a year identified as a leap year (one containing the extra
     * leap week) is reported as such by {@link AccountingChronology#isLeapYear(long)}.
     */
    @Test(timeout = 4000)
    public void isLeapYearReturnsTrueForLeapYear() throws Throwable {
        // An accounting calendar whose years end on the last Tuesday of June,
        // divided into quarters following the 4-4-5 week pattern.
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.JUNE,
                true,                                              // ends in the last week of June
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                4,                                                 // leap week falls in month 4
                4);                                                // year offset

        boolean isLeapYear = chronology.isLeapYear(4);

        assertTrue(isLeapYear);
    }
}
