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
public class AccountingChronology_ESTest_test04 extends AccountingChronology_ESTest_scaffolding {

    // Two AccountingChronology instances that share all parameters except their
    // year-division pattern must not be considered equal.
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        DayOfWeek endDay = DayOfWeek.THURSDAY;
        Month endMonth = Month.FEBRUARY;
        boolean nearestEndOfMonth = false;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology chronology544 = AccountingChronology.create(
                endDay, endMonth, nearestEndOfMonth,
                AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS,
                leapWeekInMonth, yearOffset);

        AccountingChronology chronology445 = AccountingChronology.create(
                endDay, endMonth, nearestEndOfMonth,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                leapWeekInMonth, yearOffset);

        boolean areEqual = chronology544.equals(chronology445);
        assertFalse(areEqual);
    }
}
