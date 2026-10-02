package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test09 extends BritishCutoverChronology_ESTest_scaffolding {

    // The minimum proleptic year in the supported range (-999,998)
    private static final long MIN_PROLEPTIC_YEAR = -999_998L;

    @Test(timeout = 4000)
    public void test09_isLeapYear_returnsFalseForMinimumProlepticYear() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;

        boolean isLeap = chronology.isLeapYear(MIN_PROLEPTIC_YEAR);

        assertFalse("Year -999998 is not divisible by 4, so it should not be a leap year", isLeap);
    }
}
