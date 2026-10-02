package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Month;
import java.time.DayOfWeek;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test17 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Month accountingYearEndMonth = Month.JANUARY;
        AccountingYearDivision yearDivision = AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS;
        DayOfWeek accountingYearEndDay = DayOfWeek.THURSDAY;

        AccountingChronology chronology = AccountingChronology.create(
                accountingYearEndDay,
                accountingYearEndMonth,
                true,
                yearDivision,
                1,
                1);

        AccountingEra era = AccountingEra.BCE;
        int prolepticYear = chronology.prolepticYear(era, 1);

        assertEquals(0, prolepticYear);
    }
}
