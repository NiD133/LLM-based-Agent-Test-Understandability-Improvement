package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test23 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that dateNow() returns a valid (non-null) current date for a
     * chronology whose accounting year ends on Wednesday in the last week of
     * April, divided into 4-5-4-week quarters with the leap week in month 2.
     */
    @Test(timeout = 4000)
    public void dateNowReturnsNonNullCurrentDate() throws Throwable {
        DayOfWeek yearEndsOnWednesday = DayOfWeek.WEDNESDAY;
        Month yearEndsInApril = Month.APRIL;
        boolean endsInLastWeekOfMonth = true;
        AccountingYearDivision quartersOf454Weeks = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 2;
        int yearOffset = 2;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnWednesday,
                yearEndsInApril,
                endsInLastWeekOfMonth,
                quartersOf454Weeks,
                leapWeekInMonth,
                yearOffset);

        AccountingDate currentDate = chronology.dateNow();

        assertNotNull(currentDate);
    }
}
