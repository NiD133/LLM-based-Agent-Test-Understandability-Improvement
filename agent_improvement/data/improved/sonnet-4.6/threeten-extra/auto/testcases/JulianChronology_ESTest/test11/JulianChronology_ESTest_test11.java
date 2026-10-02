package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test11 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that dateYearDay(Era, int, int) throws ClassCastException when the
     * provided Era is not a JulianEra. The Julian chronology requires its own era
     * type; passing a ThaiBuddhistEra triggers the cast guard in prolepticYear().
     */
    @Test(timeout = 4000)
    public void test11_dateYearDay_throwsClassCastException_whenEraIsNotJulianEra() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;
        ThaiBuddhistEra nonJulianEra = ThaiBuddhistEra.BEFORE_BE;

        try {
            julianChronology.dateYearDay((Era) nonJulianEra, 3, 3);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.JulianChronology", e);
        }
    }
}
