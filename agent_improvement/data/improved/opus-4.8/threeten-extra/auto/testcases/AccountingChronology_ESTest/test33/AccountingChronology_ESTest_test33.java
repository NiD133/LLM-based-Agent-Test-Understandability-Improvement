package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test33 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can build an AccountingDate from an
     * era, year-of-era, month and day-of-month.
     */
    @Test(timeout = 4000)
    public void dateFromEraYearMonthDay_returnsNonNullDate() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.MONDAY,
                Month.JANUARY,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                1,
                1);

        Era currentEra = AccountingEra.of(1);
        AccountingDate date = chronology.date(currentEra, 1, 1, 1);

        assertNotNull(date);
    }
}
