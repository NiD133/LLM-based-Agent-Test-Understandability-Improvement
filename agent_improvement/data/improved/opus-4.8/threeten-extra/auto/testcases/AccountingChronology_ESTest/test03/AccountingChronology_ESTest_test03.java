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
public class AccountingChronology_ESTest_test03 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two AccountingChronology instances that share every setting except the
     * month that holds the leap week (8 vs 4) must not be considered equal,
     * and the inequality must hold in both directions.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenLeapWeekMonthDiffers() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.SATURDAY;
        Month yearEndMonth = Month.NOVEMBER;
        boolean inLastWeek = true;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        int yearOffset = 4;

        AccountingChronology leapWeekInMonth8 =
                AccountingChronology.create(yearEndsOn, yearEndMonth, inLastWeek, division, 8, yearOffset);
        AccountingChronology leapWeekInMonth4 =
                AccountingChronology.create(yearEndsOn, yearEndMonth, inLastWeek, division, 4, yearOffset);

        assertFalse(leapWeekInMonth4.equals(leapWeekInMonth8));
        assertFalse(leapWeekInMonth8.equals((Object) leapWeekInMonth4));
    }
}
