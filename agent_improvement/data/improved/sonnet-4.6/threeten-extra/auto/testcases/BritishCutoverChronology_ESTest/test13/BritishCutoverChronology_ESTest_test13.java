package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test13 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Passing a non-JulianEra (IsoEra.BCE) to date(Era, yearOfEra, month, day)
     * must throw ClassCastException, because the chronology only accepts JulianEra.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        IsoEra nonJulianEra = IsoEra.BCE;
        try {
            BritishCutoverChronology.INSTANCE.date((Era) nonJulianEra, 60, 60, 60);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.BritishCutoverChronology", e);
        }
    }
}
