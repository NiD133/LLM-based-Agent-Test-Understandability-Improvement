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
     * Verifies that a large post-cutover year that is not divisible by 4 is not a leap year.
     *
     * Years after the 1752 cutover follow Gregorian (ISO) leap year rules.
     * Year 1000005 is not divisible by 4 (1000005 % 4 == 1), so it is not a leap year.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        long postCutoverYearNotDivisibleByFour = 1000005L;

        boolean isLeap = chronology.isLeapYear(postCutoverYearNotDivisibleByFour);

        assertFalse("Year 1000005 is not divisible by 4 and should not be a leap year", isLeap);
    }
}
