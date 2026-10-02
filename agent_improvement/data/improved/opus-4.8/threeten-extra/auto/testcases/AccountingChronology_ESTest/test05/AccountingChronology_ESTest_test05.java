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
public class AccountingChronology_ESTest_test05 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two chronologies that differ only in the month the accounting year ends on
     * (October vs. November) must not be considered equal.
     */
    @Test(timeout = 4000)
    public void chronologiesWithDifferentYearEndMonthAreNotEqual() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.SATURDAY;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        boolean endsInLastWeek = true;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology endsInOctober = AccountingChronology.create(
                yearEndsOn, Month.OCTOBER, endsInLastWeek, division, leapWeekInMonth, yearOffset);
        AccountingChronology endsInNovember = AccountingChronology.create(
                yearEndsOn, Month.NOVEMBER, endsInLastWeek, division, leapWeekInMonth, yearOffset);

        boolean areEqual = endsInNovember.equals(endsInOctober);

        assertFalse(areEqual);
    }
}
