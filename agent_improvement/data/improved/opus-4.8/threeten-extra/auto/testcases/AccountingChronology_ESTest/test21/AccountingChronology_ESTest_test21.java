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
public class AccountingChronology_ESTest_test21 extends AccountingChronology_ESTest_scaffolding {

    /**
     * AccountingChronology.create must reject a leap-week month of zero, because
     * the leap week has to fall inside a real (1-based) month. Passing 0 should
     * trigger an IllegalStateException complaining that the value cannot be zero.
     */
    @Test(timeout = 4000)
    public void createWithZeroLeapWeekMonthThrowsIllegalStateException() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.MONDAY;
        Month yearEndsInMonth = Month.JULY;
        boolean endsInLastWeekOfMonth = false;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int invalidLeapWeekInMonth = 0;
        int yearOffset = 0;

        try {
            AccountingChronology.create(
                    yearEndsOn,
                    yearEndsInMonth,
                    endsInLastWeekOfMonth,
                    division,
                    invalidLeapWeekInMonth,
                    yearOffset);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Message: "AccountingChronology leapWeekInMonth cannot be zero"
            verifyException("org.threeten.extra.chrono.AccountingChronology", e);
        }
    }
}
