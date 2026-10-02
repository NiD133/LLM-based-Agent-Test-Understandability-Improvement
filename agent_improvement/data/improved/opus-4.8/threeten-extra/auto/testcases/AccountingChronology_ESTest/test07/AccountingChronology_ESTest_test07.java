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
public class AccountingChronology_ESTest_test07 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two chronologies that share the same year division, leap-week month and
     * year offset are still considered different when their week-ending day and
     * "in last week" flag differ. Here one ends on THURSDAY (in the last week)
     * and the other ends on FRIDAY (nearest the end of month), so equals(...)
     * must return false.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenEndingDayAndLastWeekFlagDiffer() throws Throwable {
        Month yearEndMonth = Month.FEBRUARY;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = -1;

        AccountingChronology endsOnThursdayInLastWeek = AccountingChronology.create(
                DayOfWeek.THURSDAY, yearEndMonth, true, yearDivision, leapWeekInMonth, yearOffset);
        AccountingChronology endsOnFridayNearestEnd = AccountingChronology.create(
                DayOfWeek.FRIDAY, yearEndMonth, false, yearDivision, leapWeekInMonth, yearOffset);

        boolean areEqual = endsOnFridayNearestEnd.equals(endsOnThursdayInLastWeek);

        assertFalse(areEqual);
    }
}
