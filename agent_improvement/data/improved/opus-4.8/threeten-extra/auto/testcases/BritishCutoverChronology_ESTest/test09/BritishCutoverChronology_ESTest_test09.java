package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test09 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * The proleptic year -999998 is not divisible by 4, so under the Julian
     * leap-year rule that applies to years on or before the 1752 cutover it is
     * not a leap year.
     */
    @Test(timeout = 4000)
    public void isLeapYear_returnsFalse_forNonDivisibleByFourYear() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        boolean leapYear = chronology.isLeapYear(-999998L);

        assertFalse(leapYear);
    }
}
