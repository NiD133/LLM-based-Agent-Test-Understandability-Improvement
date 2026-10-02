package org.threeten.extra.chrono;

import org.junit.Test;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test01 extends AccountingChronology_ESTest_scaffolding {

    /**
     * A valid AccountingChronology can be created and its hashCode() invoked
     * without throwing. The chronology is defined as ending on a Thursday on or
     * before the end of January, with quarters following the 4-5-4 week pattern,
     * the leap week placed in month 4, and a year offset of 4.
     */
    @Test(timeout = 4000)
    public void hashCodeCanBeComputedForValidChronology() throws Throwable {
        DayOfWeek endsOn = DayOfWeek.THURSDAY;
        Month endsInMonth = Month.JANUARY;
        boolean endsInLastWeekOfMonth = false;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology chronology = AccountingChronology.create(
                endsOn, endsInMonth, endsInLastWeekOfMonth, yearDivision, leapWeekInMonth, yearOffset);

        // Simply ensure hashCode() executes without raising an exception.
        chronology.hashCode();
    }
}
