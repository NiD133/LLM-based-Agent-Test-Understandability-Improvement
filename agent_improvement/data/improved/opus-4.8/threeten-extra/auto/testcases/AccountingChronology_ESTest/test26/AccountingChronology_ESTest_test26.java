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
public class AccountingChronology_ESTest_test26 extends AccountingChronology_ESTest_scaffolding {

    /**
     * The accounting calendar has no LDML calendar-type identifier, so
     * {@link AccountingChronology#getCalendarType()} is documented to return null.
     */
    @Test(timeout = 4000)
    public void getCalendarType_isNull() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.MONDAY,
                Month.JULY,
                false,
                AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS,
                1,
                1);

        String calendarType = chronology.getCalendarType();

        assertNull(calendarType);
    }
}
