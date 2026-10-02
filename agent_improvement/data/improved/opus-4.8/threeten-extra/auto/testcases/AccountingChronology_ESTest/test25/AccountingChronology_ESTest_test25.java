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

    /**
     * The accounting calendar uses the same two eras as the ISO calendar
     * (BCE and CE), so eras() should always return exactly two values,
     * regardless of how the chronology is configured.
     */
    @Test(timeout = 4000)
    public void eras_returnsTwoEras() throws Throwable {
        AccountingChronology accountingChronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.JULY,
                false,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                4,
                -1442);

        List<Era> eras = accountingChronology.eras();

        assertEquals(2, eras.size());
    }
}
