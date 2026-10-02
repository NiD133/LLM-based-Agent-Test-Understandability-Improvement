package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.Era;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test25 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void eras_shouldReturnTwoEras() throws Throwable {
        DayOfWeek yearEndDay = DayOfWeek.WEDNESDAY;
        Month yearEndMonth = Month.JULY;
        AccountingYearDivision yearDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 4;
        int yearOffset = -1442;

        AccountingChronology chronology = AccountingChronology.create(yearEndDay, yearEndMonth, false, yearDivision, leapWeekInMonth, yearOffset);

        List<Era> eras = chronology.eras();

        assertEquals(2, eras.size());
    }
}
