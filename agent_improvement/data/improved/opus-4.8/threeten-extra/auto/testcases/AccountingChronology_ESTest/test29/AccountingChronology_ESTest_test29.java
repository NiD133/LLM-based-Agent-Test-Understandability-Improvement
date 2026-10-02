package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test29 extends AccountingChronology_ESTest_scaffolding {

    /**
     * dateNow(ZoneId) should return a non-null current AccountingDate for a
     * valid accounting calendar definition.
     */
    @Test(timeout = 4000)
    public void dateNowWithZoneReturnsNonNullDate() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.SUNDAY,
                Month.FEBRUARY,
                false,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_4_5_WEEKS,
                4,
                4);

        AccountingDate today = chronology.dateNow((ZoneId) ZoneOffset.MAX);

        assertNotNull(today);
    }
}
