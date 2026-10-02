package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test24 extends AccountingChronology_ESTest_scaffolding {

    // Verifies that dateEpochDay returns a valid AccountingDate for a chronology
    // configured to end on Wednesday in the last week of July, divided into 5-4-4 quarters,
    // with the leap week placed in month 5 and a year offset of 5.
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.WEDNESDAY;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        Month yearEndMonth = Month.JULY;
        boolean endsInLastWeek = true;
        int leapWeekInMonth = 5;
        int yearOffset = 5;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOn, yearEndMonth, endsInLastWeek, yearDivision, leapWeekInMonth, yearOffset);

        AccountingDate dateForEpochDay5 = chronology.dateEpochDay(5);

        assertNotNull(dateForEpochDay5);
    }
}
