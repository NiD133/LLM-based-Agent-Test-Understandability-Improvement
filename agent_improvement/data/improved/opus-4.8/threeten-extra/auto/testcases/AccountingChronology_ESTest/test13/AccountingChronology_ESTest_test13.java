package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test13 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can report the supported value range
     * for a temporal field (here DAY_OF_YEAR).
     */
    @Test(timeout = 4000)
    public void rangeForDayOfYearReturnsValueRange() throws Throwable {
        // Build a chronology whose accounting year ends on the last Friday of July
        // and is divided into thirteen even 4-week months, with the leap week in
        // month 4 and a year offset of 4.
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.FRIDAY,
                Month.JULY,
                true,
                AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS,
                4,
                4);

        ValueRange dayOfYearRange = chronology.range(ChronoField.DAY_OF_YEAR);

        assertNotNull(dayOfYearRange);
    }
}
