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
public class AccountingChronology_ESTest_test20 extends AccountingChronology_ESTest_scaffolding {

    /**
     * The 4-4-5 quarters division only has 12 months per year, so a leap-week
     * month index of 1426 is out of range. Creating a chronology with such an
     * index must fail with an IllegalStateException.
     */
    @Test(timeout = 4000)
    public void create_withLeapWeekMonthOutOfRange_throwsIllegalStateException() throws Throwable {
        DayOfWeek yearEndsOnWednesday = DayOfWeek.WEDNESDAY;
        Month yearEndsInJune = Month.JUNE;
        AccountingYearDivision quarters445 = AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS;
        int outOfRangeLeapWeekMonth = 1426;
        int yearOffset = -1;

        try {
            AccountingChronology.create(
                    yearEndsOnWednesday,
                    yearEndsInJune,
                    true,
                    quarters445,
                    outOfRangeLeapWeekMonth,
                    yearOffset);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Leap week cannot not be placed in non-existent month 1426, range is [1 - 12].
            verifyException("org.threeten.extra.chrono.AccountingChronology", e);
        }
    }
}
