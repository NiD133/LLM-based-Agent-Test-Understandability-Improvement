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
     * Proleptic year 0 is not a leap year for an accounting calendar whose year
     * ends on the last Tuesday in January, using 4-5-4 week quarters.
     */
    @Test(timeout = 4000)
    public void yearZeroIsNotLeapYear() throws Throwable {
        DayOfWeek yearEndsOnTuesday = DayOfWeek.TUESDAY;
        Month yearEndsInJanuary = Month.JANUARY;
        boolean endsInLastWeekOfMonth = true;
        AccountingYearDivision quartersOf454Weeks = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 9;
        int yearOffset = 0;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnTuesday,
                yearEndsInJanuary,
                endsInLastWeekOfMonth,
                quartersOf454Weeks,
                leapWeekInMonth,
                yearOffset);

        boolean isLeapYear = chronology.isLeapYear(0);

        assertFalse(isLeapYear);
    }
}
