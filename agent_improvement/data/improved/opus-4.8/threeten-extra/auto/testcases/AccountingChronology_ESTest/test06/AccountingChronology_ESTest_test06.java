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
public class AccountingChronology_ESTest_test06 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two chronologies that are identical except for the {@code inLastWeek} flag
     * must not be considered equal.
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_whenInLastWeekFlagDiffers() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.FRIDAY;
        Month yearEndsNear = Month.FEBRUARY;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology endsNearestMonthEnd = AccountingChronology.create(
                yearEndsOn, yearEndsNear, false, yearDivision, leapWeekInMonth, yearOffset);
        AccountingChronology endsInLastWeekOfMonth = AccountingChronology.create(
                yearEndsOn, yearEndsNear, true, yearDivision, leapWeekInMonth, yearOffset);

        boolean areEqual = endsNearestMonthEnd.equals(endsInLastWeekOfMonth);

        assertFalse(areEqual);
    }
}
