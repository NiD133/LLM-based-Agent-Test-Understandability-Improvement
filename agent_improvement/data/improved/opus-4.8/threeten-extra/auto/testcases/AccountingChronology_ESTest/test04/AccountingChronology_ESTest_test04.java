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
public class AccountingChronology_ESTest_test04 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Two chronologies that are identical except for how the accounting year is
     * divided into months must not be considered equal.
     */
    @Test(timeout = 4000)
    public void equals_returnsFalse_whenOnlyYearDivisionDiffers() throws Throwable {
        DayOfWeek endsOn = DayOfWeek.THURSDAY;
        Month yearEndMonth = Month.FEBRUARY;
        boolean inLastWeek = false;
        int leapWeekInMonth = 4;
        int yearOffset = 4;

        AccountingChronology quarters544 = AccountingChronology.create(
                endsOn, yearEndMonth, inLastWeek,
                AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS,
                leapWeekInMonth, yearOffset);
        AccountingChronology quarters445 = AccountingChronology.create(
                endsOn, yearEndMonth, inLastWeek,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                leapWeekInMonth, yearOffset);

        boolean areEqual = quarters544.equals(quarters445);

        assertFalse(areEqual);
    }
}
