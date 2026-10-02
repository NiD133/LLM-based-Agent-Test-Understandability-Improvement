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
     * Verifies that passing a non-JulianEra (JapaneseEra) to dateYearDay throws
     * a ClassCastException, because BritishCutoverChronology requires a JulianEra.
     */
    @Test(timeout = 4000)
    public void test_dateYearDay_withNonJulianEra_throwsClassCastException() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        Era nonJulianEra = JapaneseEra.HEISEI;

        try {
            chronology.dateYearDay(nonJulianEra, 9, 9);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Era must be JulianEra
            //
            verifyException("org.threeten.extra.chrono.BritishCutoverChronology", e);
        }
    }
}
