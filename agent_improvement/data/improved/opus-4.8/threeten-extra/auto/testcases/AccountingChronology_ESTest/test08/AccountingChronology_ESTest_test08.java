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
public class AccountingChronology_ESTest_test08 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two AccountingChronology instances created with identical parameters
     * should be considered equal.
     */
    @Test(timeout = 4000)
    public void equalsReturnsTrueForChronologiesWithSameParameters() throws Throwable {
        DayOfWeek endsOn = DayOfWeek.FRIDAY;
        Month yearEndsInMonth = Month.FEBRUARY;
        boolean endsInLastWeekOfMonth = true;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = -1;

        AccountingChronology firstChronology = AccountingChronology.create(
                endsOn, yearEndsInMonth, endsInLastWeekOfMonth, division, leapWeekInMonth, yearOffset);
        AccountingChronology secondChronology = AccountingChronology.create(
                endsOn, yearEndsInMonth, endsInLastWeekOfMonth, division, leapWeekInMonth, yearOffset);

        assertTrue(secondChronology.equals(firstChronology));
    }
}
