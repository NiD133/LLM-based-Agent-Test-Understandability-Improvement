package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test27 extends AccountingChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link AccountingChronology#dateNow(Clock)} returns a date
     * for a valid chronology when queried with the system default clock.
     */
    @Test(timeout = 4000)
    public void dateNowWithClockReturnsDate() throws Throwable {
        // An accounting calendar whose years end on a Tuesday in the last week of January,
        // divided into quarters following the 4-5-4 week pattern, with the leap week in month 1.
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.JANUARY,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                1,
                1);

        Clock systemClock = MockClock.systemDefaultZone();
        AccountingDate today = chronology.dateNow(systemClock);

        assertNotNull(today);
    }
}
