package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test11 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * dateYearDay(Era, yearOfEra, dayOfYear) only accepts a JulianEra. Passing an
     * era of any other type (here a JapaneseEra) must be rejected with a
     * ClassCastException reporting "Era must be JulianEra".
     */
    @Test(timeout = 4000)
    public void dateYearDayRejectsNonJulianEra() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        Era nonJulianEra = JapaneseEra.HEISEI;

        try {
            chronology.dateYearDay(nonJulianEra, 9, 9);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Era must be JulianEra
            verifyException("org.threeten.extra.chrono.BritishCutoverChronology", e);
        }
    }
}
