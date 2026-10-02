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
public class AccountingChronology_ESTest_test01 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hashCode_doesNotThrowForValidChronology() throws Throwable {
        Month endMonth = Month.JANUARY;
        DayOfWeek yearEndDay = DayOfWeek.THURSDAY;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = 4;
        AccountingChronology chronology = AccountingChronology.create(yearEndDay, endMonth, false, division, leapWeekInMonth, yearOffset);
        chronology.hashCode();
    }
}
