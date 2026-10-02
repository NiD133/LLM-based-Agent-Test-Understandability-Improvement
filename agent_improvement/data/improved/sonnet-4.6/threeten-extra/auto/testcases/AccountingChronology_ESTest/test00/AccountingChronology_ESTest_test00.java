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
public class AccountingChronology_ESTest_test00 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that hashCode() completes without throwing for a valid
     * AccountingChronology configured with:
     *   - year ends on Wednesday
     *   - in the last week of April
     *   - year divided into 4-5-4 quarter pattern
     *   - leap week placed in period 2
     *   - year offset of 2
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        DayOfWeek endsOnWednesday = DayOfWeek.WEDNESDAY;
        Month endsInApril = Month.APRIL;
        AccountingYearDivision quarterPattern454 = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        boolean yearEndsInLastWeek = true;
        int leapWeekInPeriod = 2;
        int yearOffset = 2;

        AccountingChronology chronology = AccountingChronology.create(
                endsOnWednesday, endsInApril, yearEndsInLastWeek, quarterPattern454, leapWeekInPeriod, yearOffset);

        chronology.hashCode();
    }
}
