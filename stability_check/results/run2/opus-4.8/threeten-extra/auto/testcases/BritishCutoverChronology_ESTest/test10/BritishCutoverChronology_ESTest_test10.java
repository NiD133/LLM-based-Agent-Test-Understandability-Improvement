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
     * A year after the 1752 cutover follows the ISO leap-year rule.
     * The year 1,000,005 is not divisible by 4, so it is not a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYear_returnsFalse_forNonLeapYearAfterCutover() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        boolean leap = chronology.isLeapYear(1000005L);

        assertFalse(leap);
    }
}
