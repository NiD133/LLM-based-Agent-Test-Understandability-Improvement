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

    /**
     * Verifies that {@link AccountingChronology#toString()} renders a human-readable
     * description that reflects every configuration parameter supplied to
     * {@link AccountingChronology#create}.
     *
     * <p>The chronology under test is configured so that:
     * <ul>
     *   <li>the accounting year ends on a SUNDAY nearest the end of OCTOBER
     *       ({@code inLastWeek = false} produces "nearest end of");</li>
     *   <li>the year is divided into QUARTERS_OF_PATTERN_4_5_4_WEEKS;</li>
     *   <li>the leap-week lives in month 5;</li>
     *   <li>the year offset is 0 ({@code yearOffset = 0} produces "ending in the given ISO year").</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void toString_describesAllChronologyParameters() throws Throwable {
        DayOfWeek yearEndsOnSunday = DayOfWeek.SUNDAY;
        Month yearEndsNearOctober = Month.OCTOBER;
        boolean endsInLastWeek = false;
        AccountingYearDivision quarterDivision = AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS;
        int leapWeekInMonth = 5;
        int yearOffset = 0;

        AccountingChronology chronology = AccountingChronology.create(
                yearEndsOnSunday, yearEndsNearOctober, endsInLastWeek, quarterDivision, leapWeekInMonth, yearOffset);

        String description = chronology.toString();

        assertEquals(
                "Accounting calendar ends on SUNDAY nearest end of OCTOBER, "
                        + "year divided in QUARTERS_OF_PATTERN_4_5_4_WEEKS "
                        + "with leap-week in month 5 ending in the given ISO year",
                description);
    }
}
