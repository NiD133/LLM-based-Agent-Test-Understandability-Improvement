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
public class AccountingChronology_ESTest_test34 extends AccountingChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test34() throws Throwable {
        // Build a chronology ending on SUNDAY nearest end of OCTOBER,
        // divided into 4-5-4 quarters with the leap-week in month 5,
        // aligned to the ending ISO year (yearOffset=0).
        DayOfWeek endsOn = DayOfWeek.SUNDAY;
        Month endMonth = Month.OCTOBER;
        AccountingYearDivision division = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 5;
        int yearOffset = 0;
        boolean inLastWeek = false;

        AccountingChronology chronology = AccountingChronology.create(endsOn, endMonth, inLastWeek, division, leapWeekInMonth, yearOffset);

        String expectedDescription =
                "Accounting calendar ends on SUNDAY nearest end of OCTOBER, " +
                "year divided in QUARTERS_OF_PATTERN_4_5_4_WEEKS " +
                "with leap-week in month 5 ending in the given ISO year";

        assertEquals(expectedDescription, chronology.toString());
    }
}
