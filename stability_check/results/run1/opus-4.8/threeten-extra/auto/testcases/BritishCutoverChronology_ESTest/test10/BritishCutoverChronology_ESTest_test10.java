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
     * A year that is not divisible by 4 (here 1,000,005, which lies well after
     * the 1752 cutover) is not a leap year in the British cutover calendar.
     */
    @Test(timeout = 4000)
    public void isLeapYear_returnsFalseForNonLeapYearAfterCutover() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        boolean isLeapYear = chronology.isLeapYear(1000005L);

        assertFalse(isLeapYear);
    }
}
