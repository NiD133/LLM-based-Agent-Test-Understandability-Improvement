package org.threeten.extra.chrono;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test00 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link AccountingChronology#hashCode()} can be invoked on a
     * fully-configured chronology without throwing.
     */
    @Test(timeout = 4000)
    public void hashCodeDoesNotThrowOnConfiguredChronology() throws Throwable {
        DayOfWeek yearEndsOn = DayOfWeek.WEDNESDAY;
        Month monthYearEndsNear = Month.APRIL;
        boolean endsInLastWeekOfMonth = true;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 2;
        int yearOffset = 2;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOn,
                monthYearEndsNear,
                endsInLastWeekOfMonth,
                yearDivision,
                leapWeekInMonth,
                yearOffset);

        chronology.hashCode();
    }
}
