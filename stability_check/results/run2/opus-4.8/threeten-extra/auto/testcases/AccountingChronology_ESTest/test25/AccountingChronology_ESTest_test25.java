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
     * The accounting calendar defines exactly two eras (BCE and CE, mirroring
     * ISO), so {@link AccountingChronology#eras()} should always return a list
     * of size 2, regardless of how the chronology is configured.
     */
    @Test(timeout = 4000)
    public void erasReturnsTheTwoAccountingEras() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.JULY,
                false,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                4,
                -1442);

        List<Era> eras = chronology.eras();

        assertEquals(2, eras.size());
    }
}
