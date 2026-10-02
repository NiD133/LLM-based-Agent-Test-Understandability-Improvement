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
public class AccountingChronology_ESTest_test24 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can convert an epoch-day value into
     * an AccountingDate. The chronology is configured to end on the last
     * Wednesday in/near July, divide its year into 5-4-4 week quarters, place
     * the leap week in month 5, and use a year offset of 5.
     */
    @Test(timeout = 4000)
    public void dateEpochDayReturnsAccountingDate() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.JULY,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_5_4_4_WEEKS,
                5,
                5);

        AccountingDate dateFromEpochDay = chronology.dateEpochDay(5);

        assertNotNull(dateFromEpochDay);
    }
}
