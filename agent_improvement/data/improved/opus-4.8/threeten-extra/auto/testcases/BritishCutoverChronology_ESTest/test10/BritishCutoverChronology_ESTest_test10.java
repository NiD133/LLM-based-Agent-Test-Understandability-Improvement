package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test10 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * A proleptic year after the 1752 cutover follows the Gregorian (ISO) leap-year
     * rule. The year 1000005 is not divisible by 4, so it is not a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYear_returnsFalseForNonLeapYearAfterCutover() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        boolean leapYear = chronology.isLeapYear(1000005L);

        assertFalse(leapYear);
    }
}
