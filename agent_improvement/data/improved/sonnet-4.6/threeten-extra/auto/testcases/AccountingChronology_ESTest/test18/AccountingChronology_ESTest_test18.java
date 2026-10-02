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
    public void isLeapYear_returnsFalse_forYearZero_withTuesdayJanuaryLastWeekQuarters454() throws Throwable {
        DayOfWeek endsOnTuesday = DayOfWeek.TUESDAY;
        Month endMonth = Month.JANUARY;
        AccountingYearDivision quarters454 = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        AccountingChronology chronology = AccountingChronology.create(endsOnTuesday, endMonth, true, quarters454, 9, 0);

        boolean isLeap = chronology.isLeapYear(0);

        assertFalse("Year 0 should not be a leap year in this accounting chronology", isLeap);
    }
}
