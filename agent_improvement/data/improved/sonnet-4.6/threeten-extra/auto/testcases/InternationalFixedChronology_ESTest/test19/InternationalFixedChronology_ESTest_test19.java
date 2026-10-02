package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test19 extends InternationalFixedChronology_ESTest_scaffolding {

    // The International Fixed calendar has 365 days in a non-leap year (13 months × 28 days + 1 year-day).
    private static final int DAYS_IN_NON_LEAP_YEAR = 365;

    @Test(timeout = 4000)
    public void test_dateNow_lengthOfYear_returnsStandardYearLength() throws Throwable {
        InternationalFixedDate today = InternationalFixedChronology.INSTANCE.dateNow();
        assertEquals(DAYS_IN_NON_LEAP_YEAR, today.lengthOfYear());
    }
}
