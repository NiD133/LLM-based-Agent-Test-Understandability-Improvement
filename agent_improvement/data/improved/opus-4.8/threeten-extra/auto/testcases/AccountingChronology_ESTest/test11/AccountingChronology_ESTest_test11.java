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
public class AccountingChronology_ESTest_test11 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that an AccountingChronology can report the valid value range
     * for the PROLEPTIC_MONTH field. The chronology is built with the
     * "13 even months of 4 weeks" division, for which range(PROLEPTIC_MONTH)
     * returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForProlepticMonthIsNotNull() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.SATURDAY,
                Month.JANUARY,
                /* inLastWeek */ true,
                AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS,
                /* leapWeekInMonth */ 4,
                /* yearOffset */ 4);

        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);

        assertNotNull(prolepticMonthRange);
    }
}
