package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test10 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Years after the 1752 cutover follow Gregorian (ISO) leap-year rules.
     * 1,000,005 is not divisible by 4, so it is not a leap year.
     */
    @Test(timeout = 4000)
    public void test_isLeapYear_postCutoverNonLeapYear_returnsFalse() throws Throwable {
        long nonLeapYear = 1_000_005L; // post-cutover, not divisible by 4
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;
        boolean isLeap = chronology.isLeapYear(nonLeapYear);
        assertFalse(isLeap);
    }
}
