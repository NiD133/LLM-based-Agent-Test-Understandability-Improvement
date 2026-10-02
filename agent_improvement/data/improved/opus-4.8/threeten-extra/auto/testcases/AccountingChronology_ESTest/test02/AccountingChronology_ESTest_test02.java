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
public class AccountingChronology_ESTest_test02 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two chronologies that are identical except for their year-offset
     * (5 versus 1752) must not be considered equal, and the inequality
     * must hold symmetrically in both directions.
     */
    @Test(timeout = 4000)
    public void chronologiesDifferingOnlyByYearOffsetAreNotEqual() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.SATURDAY;
        Month yearEndMonth = Month.MARCH;
        boolean inLastWeek = true;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS;
        int leapWeekInMonth = 5;

        AccountingChronology chronologyWithOffset5 = AccountingChronology.create(
                yearEndsOn, yearEndMonth, inLastWeek, division, leapWeekInMonth, 5);
        AccountingChronology chronologyWithOffset1752 = AccountingChronology.create(
                yearEndsOn, yearEndMonth, inLastWeek, division, leapWeekInMonth, 1752);

        assertFalse(chronologyWithOffset5.equals(chronologyWithOffset1752));
        assertFalse(chronologyWithOffset1752.equals((Object) chronologyWithOffset5));
    }
}
