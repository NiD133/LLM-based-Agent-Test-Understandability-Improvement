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

    @Test(timeout = 4000)
    public void test_isLeapYear_returnsFalse_forProlepticYearZero() throws Throwable {
        // Build a chronology ending on Tuesdays in the last week of January,
        // with quarters in a 4-5-4 week pattern and the leap week placed in month 9
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.JANUARY,
                /*inLastWeek=*/ true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                /*leapWeekInMonth=*/ 9,
                /*yearOffset=*/ 0);

        assertFalse(chronology.isLeapYear(0));
    }
}
