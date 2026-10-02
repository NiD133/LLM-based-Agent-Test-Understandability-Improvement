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
public class AccountingChronology_ESTest_test23 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        // Build an accounting chronology that ends on Wednesday in the last week of April,
        // uses a 4-5-4 quarter pattern, places the leap week in month 2, with year offset 2.
        DayOfWeek yearEndDay = DayOfWeek.WEDNESDAY;
        Month yearEndMonth = Month.APRIL;
        boolean endsInLastWeek = true;
        AccountingYearDivision quarterDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 2;
        int yearOffset = 2;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndDay, yearEndMonth, endsInLastWeek, quarterDivision, leapWeekInMonth, yearOffset);

        // Verify that retrieving the current date from this chronology succeeds and returns a result.
        AccountingDate today = chronology.dateNow();
        assertNotNull(today);
    }
}
