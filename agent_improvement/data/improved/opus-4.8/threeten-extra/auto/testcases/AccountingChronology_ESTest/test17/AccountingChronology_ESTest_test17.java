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
public class AccountingChronology_ESTest_test17 extends AccountingChronology_ESTest_scaffolding {

    /**
     * For the BCE (Before Current Era) era, the proleptic year is computed as
     * {@code 1 - yearOfEra}. Year-of-era 1 in BCE therefore maps to proleptic
     * year 0.
     */
    @Test(timeout = 4000)
    public void prolepticYearForBceYearOneIsZero() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.THURSDAY,
                Month.JANUARY,
                true,
                AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS,
                1,
                1);

        int prolepticYear = chronology.prolepticYear(AccountingEra.BCE, 1);

        assertEquals(0, prolepticYear);
    }
}
