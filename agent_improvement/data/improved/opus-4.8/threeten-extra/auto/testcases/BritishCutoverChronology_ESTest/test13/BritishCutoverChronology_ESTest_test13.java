package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test13 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * BritishCutoverChronology requires its era to be a JulianEra. Passing an
     * IsoEra to date(Era, year, month, day) must therefore fail with a
     * ClassCastException ("Era must be JulianEra").
     */
    @Test(timeout = 4000)
    public void date_withNonJulianEra_throwsClassCastException() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        Era nonJulianEra = IsoEra.BCE;

        try {
            chronology.INSTANCE.date(nonJulianEra, 60, 60, 60);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Era must be JulianEra
            verifyException("org.threeten.extra.chrono.BritishCutoverChronology", e);
        }
    }
}
