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
public class AccountingChronology_ESTest_test19 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
            DayOfWeek.TUESDAY,
            Month.JUNE,
            true,
            AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
            4,
            4
        );

        assertTrue(chronology.isLeapYear(4));
    }
}
